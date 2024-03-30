// 地图大小
const width = 800, height = 600;

const gridSize = 60; // 网格大小，确保每个网格的宽和高至少为30
const cols = Math.floor(width / gridSize); // 计算地图上的列数
const rows = Math.floor(height / gridSize); // 计算地图上的行数
const usedGrids = new Set(); // 用于记录已经使用的网格

const generatePointInGrid = (gridX, gridY) => {
    // 在指定的网格内随机生成一个点的坐标
    const x = gridX * gridSize + Math.random() * gridSize;
    const y = gridY * gridSize + Math.random() * gridSize;
    return { x, y };
};
const mainBuildingTypes = ["teachingBuildings", "officeBuildings", "dormitoryBuildings"];
const secondaryBuildingTypes = [
  "shops", "restaurants", "restrooms", "libraries",
  "canteens", "supermarkets", "cafes", "parkingLots",
  "restAreas", "gymnasiums", "sportsFields", "lectureHalls",
  "landscapingAreas", "landmarks"
];
const generatePoints = (count) => {
    const points = {};
    let mainBuildingsCount = 0;
    let secondaryBuildingsCounts = new Map(secondaryBuildingTypes.map(type => [type, 0]));
    let totalSecondaryBuildingsCount = 0;
    let lastType = null; // 记录上一个点的类型
    let buildingsSinceLastCrossing = 0; // 自上一个crossing点以来的建筑物数量
    for (let i = 0; i < count; i++) {
        let x, y, type, tooClose;
        do {
            tooClose = false;
            x = Math.random() * width;
            y = Math.random() * height;

            // 边界检查，确保点不会超出SVG容器
            x = Math.max(30, Math.min(x, width - 30)); 
            y = Math.max(30, Math.min(y, height - 30));

            // 确定点的类型
            if (lastType === "crossing") {
                // 如果上一个点是crossing，则当前点不能是crossing
                // 此处无需额外的随机逻辑判断
                type = mainBuildingTypes[Math.floor(Math.random() * mainBuildingTypes.length)]; // 默认分配一个主要建筑类型
                mainBuildingsCount++;
                buildingsSinceLastCrossing++;
            } else if (buildingsSinceLastCrossing >= 3) {
                // 每隔5个建筑物生成一个crossing点
                type = "crossing";
                buildingsSinceLastCrossing = 0; // 重置计数器
            } else if (mainBuildingsCount < 20 || (mainBuildingsCount<35 && Math.random() > 0.5)||Math.random() > 0.9) {
                // 更倾向于创建主要建筑，直到满足最低数量要求
                type = mainBuildingTypes[Math.floor(Math.random() * mainBuildingTypes.length)];
                mainBuildingsCount++;
                buildingsSinceLastCrossing++;
            } else if (totalSecondaryBuildingsCount < 50 || Math.random() > 0.85) {
                // 随机分配次要建筑类型
                type = secondaryBuildingTypes[Math.floor(Math.random() * secondaryBuildingTypes.length)];
                secondaryBuildingsCounts.set(type, (secondaryBuildingsCounts.get(type) || 0) + 1);
                totalSecondaryBuildingsCount++;
                buildingsSinceLastCrossing++;
            } else {
                type = "crossing";
                mainBuildingsCount++;
                buildingsSinceLastCrossing++;
            }
            

            // 检查新点是否与现有点太近
            for (let existingId of Object.keys(points)) {
                const existingPoint = points[existingId];
                const dist = Math.sqrt(Math.pow(existingPoint.x - x, 2) + Math.pow(existingPoint.y - y, 2));
                if (dist < 50) {
                    tooClose = true;
                    break;
                }
            }
        } while (tooClose);
        lastType=type
        points[`point${i}`] = {
            pointId: `point${i}`,
            mapId: 'map1',
            x: x,
            y: y,
            type: type,
            width: 30,
            length: 30,
            neighbors: []
        };
    }

    // 确保每种次要建筑物至少有一个
    secondaryBuildingTypes.forEach(type => {
        if (secondaryBuildingsCounts.get(type) === 0) {
            // 如果某个类型的建筑没有被创建，找一个类型为"crossing"的点改变其类型
            let changed = false;
            for (let point of Object.values(points)) {
                if (point.type === "crossing") {
                    point.type = type;
                    changed = true;
                    break;
                }
            }
            if (!changed) {
                console.error(`Failed to ensure at least one ${type}.`);
            }
        }
    });

    return points;
};

const calculateDistance = (pointA, pointB) => {
    return Math.sqrt((pointA.x - pointB.x) ** 2 + (pointA.y - pointB.y) ** 2);
};


// 生成边
const generateEdges = (points) => {
    const pointIds = Object.keys(points);

    pointIds.forEach(sourceId => {
        let minDistance = Infinity;
        let closestPointId = null;

        pointIds.forEach(destinationId => {
            if (sourceId !== destinationId) {
                const distance = calculateDistance(points[sourceId], points[destinationId]);

                // 更新最近点信息
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPointId = destinationId;
                }

                // 如果距离小于或等于85，则创建边
                if (distance <= 85) {
                    // 确保每个点的neighbors属性是一个数组
                    if (!points[sourceId].neighbors) {
                        points[sourceId].neighbors = [];
                    }
                    points[sourceId].neighbors.push({
                        destination: destinationId, // 记录目标点的ID
                        distance: distance
                    });
                }
            }
        });

        // 确保每个点至少有一个边连接
        if (!points[sourceId].neighbors || points[sourceId].neighbors.length === 0) {
            points[sourceId].neighbors = [{
                destination: closestPointId,
                distance: minDistance
            }];
        }
    });
};



// 创建图
const createGraph = () => {
    const points = generatePoints(120);
    generateEdges(points);

    return { points };
};

document.addEventListener('DOMContentLoaded', function() {
    // 创建SVG容器
    const svg = d3.select("#map").append("svg")
        .attr("width", width)
        .attr("height", height);

    // 在SVG内部添加一个g元素用于包含所有绘图元素
    const g = svg.append("g");

    // 应用缩放行为于SVG，并传递缩放和平移的变换到g元素
    svg.call(d3.zoom().on("zoom", event => {
        g.attr("transform", event.transform);
    }));

    // 生成图形数据并绘制图
    const graphData = createGraph();
    drawGraph(graphData, g); // 确保传递g元素到drawGraph函数

     // 添加保存按钮的点击事件
     document.getElementById('saveBtn').addEventListener('click', function() {
        // 将graphData转换为字符串
        const dataStr = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(graphData));
        // 创建一个临时的a标签用于下载
        const downloadAnchorNode = document.createElement('a');
        downloadAnchorNode.setAttribute("href", dataStr);
        // 使用当前时间作为文件名
        const date = new Date();
        const filename = `graphData-${date.getFullYear()}${date.getMonth()+1}${date.getDate()}-${date.getHours()}${date.getMinutes()}${date.getSeconds()}.json`;
        downloadAnchorNode.setAttribute("download", filename);
        document.body.appendChild(downloadAnchorNode); // required for firefox
        downloadAnchorNode.click();
        downloadAnchorNode.remove();
    });
});

// 修改drawGraph函数以接受g元素作为参数
const drawGraph = (graphData, g) => {
    // 绘制边界
    g.append("rect")
        .attr("x", 0)
        .attr("y", 0)
        .attr("width", width)
        .attr("height", height)
        .attr("fill", "none")
        .attr("stroke", "black")
        .attr("stroke-width", 2);


// 绘制边
Object.values(graphData.points).forEach(point => {
    point.neighbors.forEach(neighbor => {
        // 获取目标点的完整信息
        const destinationPoint = graphData.points[neighbor.destination];

        g.append("line")
            .attr("x1", point.x) // 起点坐标直接从外层循环的点获取
            .attr("y1", point.y)
            .attr("x2", destinationPoint.x) // 终点坐标从目标点信息中获取
            .attr("y2", destinationPoint.y)
            .attr("stroke", "black")
            .attr("stroke-opacity", 0.5);
    });
});

Object.values(graphData.points).forEach(point => {
    let fillColor;
    // 判断点的类型并选择颜色
    if (mainBuildingTypes.includes(point.type)) {
        fillColor = "blue"; // 主要建筑物为蓝色
    } else if (secondaryBuildingTypes.includes(point.type)) {
        fillColor = "green"; // 次要建筑物为绿色
    } else if (point.type === "crossing") {
        fillColor = "grey"; // 路口为灰色
    }

    // 根据类型绘制相应的图形
    if (point.type !== "crossing") {
        // 建筑物使用矩形表示
        g.append("rect")
            .attr("x", point.x - point.width / 2)
            .attr("y", point.y - point.length)
            .attr("width", point.width)
            .attr("height", point.length)
            .attr("fill", fillColor);
    } else {
        // 路口使用圆形表示
        g.append("circle")
            .attr("cx", point.x)
            .attr("cy", point.y)
            .attr("r", 3)
            .attr("fill", fillColor);
    }
});
};