$(function () {
    $(".step-image").each(function () {
        var element = $(this);
        var url = '/api/recipes/' + element.data('recipeId') + '/steps/' + element.data('stepId');
        $.ajax({
            type: "GET",
            url: url,
            success: function (step) {
                element.attr('src', `data:image/jpeg;base64,` + btoa(String.fromCharCode.apply(null, new Uint8Array(step.image))));
            }
        })
    });
});

$('#form').submit(function (e) {
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

$('#editForm').submit(function (e) {
    e.preventDefault();

    var dataFrom = new FormData($(this)[0]);

    var url = '/api' + $(this).attr('action');

    $.ajax({
        type: "PUT",
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

