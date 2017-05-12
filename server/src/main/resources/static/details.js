$(document).ready(function () {
    $("#alertDiv").hide();
});

$("#queryBtn").click(function(){
    // Get back to initial html
    $("#grid-basic").bootgrid("destroy");

    // Spinning to wait for visualization complete
    $('#spinner').modal('show');

    // Start loading data and rendering charts
    var i = $("#scenario").prop('selectedIndex');
    var id = "loss";
    if (i < 3) {
        id = "loss";
    }
    else if (i < 6) {
        id = "rtt";
        i -= 3;
    }
    else {
        id = "bw";
        i -= 6;
    }

    var j = $("#subject").prop('selectedIndex');
    renderGrid(id, i, j);
    renderBarChart(id, i, j);
});

function renderGrid(idVal, scenario, subject) {
    $("#grid-basic").bootgrid({
        ajax: true,
        post: function ()
        {
            /* To accumulate custom parameter with the request object */
            return {
                id: idVal,
                scenario: scenario,
                subject: subject
            };
        },
        url: "/detail"
    });

    $("#grid-basic").on("click.rs.jquery.bootgrid", function(e, columns, row) {
       BootstrapDialog.show({
            message:
            '<div style="height: 800px;>' +
               '<div class="col-sm-12"><table id="grid-moredetail" class="table table-condensed table-hover table-striped">' +
               '<thead><tr><th data-column-id="uri">Uri</th><th data-column-id="startTime">Start Time</th><th data-column-id="endTime">End Time</th></tr></thead><tbody>' +
                '</tbody></table></div>' +
                '<script>$("#grid-moredetail").bootgrid({ ajax: true, post: function () {' +
                'return {id : "' + idVal + '", scenario: '+ scenario + ', subject:' + subject + ', rowId:' + row.id + '};}, url: "/moredetail" });</script>' +
            '</div>'
        });
    });
}

function renderBarChart(id, i, j) {
    d3.json('/detail?id=' + id + '&scenario=' + i + '&subject=' + j, function (error, data) {
        doRender("#cdfChart", data);
        $('#spinner').modal('hide');
    });
}

function doRender(chartId, data) {
    if (data == null) return;

    var cdf = calculateCdf(data.rows);
    var chart = dc.lineChart(chartId);
    var ndx = crossfilter(cdf);
    var dim = ndx.dimension(function (d) { return d.elapse; });
    var grp = dim.group().reduceSum(function (d) { return d.probability; });

    var height = 400, width = 900;
    chart.width(width)
        .height(height)
        .interpolate('basis')
        .x(d3.scale.ordinal())
        .xUnits(dc.units.ordinal)
        .brushOn(false)
        .xAxisLabel('PLT (ms)')
        .yAxisLabel('CDF')
        .dimension(dim)
        .group(grp);

    chart.render();
}

function CdfPoint(elapse, probability) {
    this.elapse = elapse;
    this.probability = probability;
}

function calculateCdf(data) {
    var time = [];
    for (var i = 0; i < data.length; i++) {
        time[i] = data[i].elapse;
    }
    var max = arr.max(time), min = arr.min(time);
    var d = gaussian(arr.mean(time), arr.variance(time));

    var cdf = [];
    var x = [
        min,
        min + (max - min) / 4,
        min + (max - min) / 2,
        min + (max - min) * 3 / 4,
        max,
        max + 100];
    for (var i = 0; i < x.length; i++) {
        cdf[i] = new CdfPoint(
            parseInt(x[i]),
            d.cdf(x[i]));
    }
    return cdf;
}
