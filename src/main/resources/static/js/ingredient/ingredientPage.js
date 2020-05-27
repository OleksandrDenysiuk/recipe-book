$('form').submit(function (e) {
    e.preventDefault();
    var data = {
        name: $('#nameIngredient').val(),
        amount: $('#amountIngredient').val(),
        measure: $('#measureIngredient').val()
    };

    console.log(data);
    $.ajax({
        type: "POST",
        url: "/api/recipes/1/ingredients/create",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function(response) {

        }
    });
});