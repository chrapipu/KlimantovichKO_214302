google.charts.load("current", {packages: ["corechart"]});
google.charts.setOnLoadCallback(draw);
google.charts.setOnLoadCallback(drawCat);

function draw() {
    let res = [['Название', 'Количество']];

    for (let i = 0; i < stringIncomes.length; i++) {
        res.push([stringIncomes[i], intIncomes[i]]);
    }

    var data = google.visualization.arrayToDataTable(res);

    let options = {
        title: 'Топ-5 продаваемых товаров',
        hAxis: {title: 'Название'},
        vAxis: {title: 'Количество'},
        bar: {groupWidth: "80%"},
        legend: {position: "none"}
    };

    let chart = new google.visualization.ColumnChart(document.getElementById('draw'));
    chart.draw(data, options);
}

function drawCat() {
    let res = [['Категория', 'Прибыль']];

    for (let i = 0; i < catString.length; i++) {
        res.push([catString[i], catInt[i]]);
    }

    var data = google.visualization.arrayToDataTable(res);

    var options = {
        title: 'Прибыль по категориям',
        pieHole: 0.2,
    };

    var chart = new google.visualization.PieChart(document.getElementById('drawCat'));
    chart.draw(data, options);
}