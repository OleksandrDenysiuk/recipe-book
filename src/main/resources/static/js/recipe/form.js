var recipeId = $('#recipeId').val();

$(function () {
    if (recipeId !== undefined) {
        $.ajax({
            type: 'GET',
            url: '/api/recipes/' + recipeId,
            success: function (recipe) {
                setDataForm(recipe);
            }
        })
    }
});


function setDataForm(recipe) {
    $('#title').val(recipe.title);
    $('#description').val(recipe.description);
    $('#cookTime').val(recipe.cookTime);
    $('#difficulty').val(recipe.difficulty);
}

$('#submitBtn').click(function () {

    var dataForm = new FormData($('#form')[0]);

    console.log(dataForm);

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
        },
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
        },
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
        }
    });
}
