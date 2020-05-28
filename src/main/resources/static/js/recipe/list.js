$(function () {
    $(".recipe-list-image img").each(function () {
        var element = $(this);
        var url = '/api/recipes/' + element.data('recipeId');
        $.ajax({
            type: "GET",
            url: url,
            success: function (recipe) {
                element.attr('src', `data:image/jpeg;base64,` + btoa(String.fromCharCode.apply(null, new Uint8Array(recipe.image))));
            }
        })
    });
});
