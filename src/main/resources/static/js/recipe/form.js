var recipeId = $('#recipeId').val();

$(function () {
    if (recipeId !== undefined) {
        $.ajax({
            type: 'GET',
            url: '/api/recipes/' + recipeId,
            success: function (recipe) {
                console.log(recipe);
                setDataForm(recipe);
            }
        })
    }
});


function setDataForm(recipe) {
    $('#titleRecipe').val(recipe.title);
    $('#descriptionRecipe').val(recipe.description);
    $('#cookTimeRecipe').val(recipe.cookTime);
    $('#difficulty').val(recipe.difficulty);
}

$('#submitBtn').click(function () {

    var dataForm = new FormData($('#form')[0]);

    console.log(recipeId);

    if (recipeId === undefined) {
        createRecipe(dataForm);
    } else {
        updateRecipe(dataForm, recipeId);
    }
});

function createRecipe(dataForm) {
    $.ajax({
        type: 'POST',
        enctype: 'multipart/form-data',
        processData: false,  // Important!
        contentType: false,
        cache: false,
        url: '/api/recipes',
        data: dataForm,
        success: function (response) {
            location.href = '/recipes/' + response.id + '/ingredients';
        }
    });
}

function updateRecipe(dataForm, recipeId) {
    $.ajax({
        type: 'PUT',
        enctype: 'multipart/form-data',
        processData: false,  // Important!
        contentType: false,
        cache: false,
        url: '/api/recipes/' + recipeId,
        data: dataForm,
        success: function (response) {
            location.href = '/recipes/' + response.id + '/ingredients';
        }
    });
}
