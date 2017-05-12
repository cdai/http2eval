$(document).ready(function () {
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
}
