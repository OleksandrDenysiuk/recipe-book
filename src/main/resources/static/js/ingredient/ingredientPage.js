$('#submitBtn').click(function () {
    var data = {
        name: $('#name').val(),
        amount: $('#amount').val(),
        measure: $('#measure').val()
    };

    var url = '/api/recipes/' + $('#recipeId').val() + '/ingredients';

    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/json",
        data: JSON.stringify(data),
        error: function (response) {
            var errors = response.responseJSON;

            $( ".is-invalid" ).each(function( index ) {
                $(this).toggleClass('is-invalid');
            });

            $.each(errors, function( index, value ) {
                var input = $('#' + index);
                input.addClass('is-invalid');
                input.closest('div').find('.invalid-feedback').text(value);
            });
        },
        success: function() {
            location.reload();
        }
    });
});

$('.delete-link').click(function (event) {
    event.preventDefault();
    var url = '/api' + $(this).attr('href');
    $.ajax({
        type: 'DELETE',
        url: url,
        success: function () {
            location.reload();
        }
    });
});