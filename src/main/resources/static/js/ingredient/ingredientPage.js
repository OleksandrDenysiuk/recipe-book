$('#submitBtn').click(function () {
    var data = {
        name: $('#nameIngredient').val(),
        amount: $('#amountIngredient').val(),
        measure: $('#measureIngredient').val()
    };

    var url = '/api/recipes/' + $('#recipeId').val() + '/ingredients';

    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function(response) {
            location.reload();
        }
    });
});

$('#delete').click(function (event) {
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