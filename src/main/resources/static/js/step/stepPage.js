$('form').submit(function (e) {
    e.preventDefault();

    var dataFrom = new FormData($('#form')[0]);

    var url = '/api/recipes/' + $('#recipeId').val() + '/steps';

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        processData: false,  // Important!
        contentType: false,
        cache: false,
        url: url,
        data: dataFrom,
        success: function (response) {
            location.reload();
        }
    });
});

$('#delete').click(function (event) {
    event.preventDefault();

    var url = '/api/recipes/'+ $('#recipeId').val() + $(this).attr('href');

    $.ajax({
        type: 'DELETE',
        url: url,
        success: function () {
            location.reload();
        }
    });
});