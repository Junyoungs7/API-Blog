var main = {
    init : function (){
        var _this = this;
        $('#btn-save').on('click', function (){
            _this.save();
        });
        $('#btn-update').on('click', function (){
            _this.update();
        });
        $('#btn-delete').on('click', function (){
            _this.delete();
        });
    },
    save : function (){
        var data = {
            city1: $('#addressKindU').val(),
            city2: $('#addressKindD').val()
        };
        alert($('#addressKindU').val(),$('#addressKindD').val());
        console.log($('#addressKindU').val(),$('#addressKindD').val());

        $.ajax({
            type: 'POST',
            url: '/weathermain/main',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(data)
        }).done(function (){
            alert('content submit');
            window.location.href='/weathermain/main';
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },
    update : function (){
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('modified content complete');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function (){
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function (){
            alert('delete content');
            window.location.href = '/';
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    }

};

main.init();