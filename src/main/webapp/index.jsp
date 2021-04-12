<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="ru">
<head>
    <!-- Required meta tags -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"
            integrity="sha384-SR1sx49pcuLnqZUnnPwx6FCym0wLsk5JZuNx2bPPENzswTNFaQU1RDvt3wT4gWFG" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js"
            integrity="sha384-j0CNLUeiqtyaRmlzUHCPZ+Gy5fQu0dQ6eZ/xAww941Ai1SxSY+0EQqNXNE6DZiVc" crossorigin="anonymous"></script>
    <title>Список дел</title>
    <script>
        function addNewItem() {
            const item = {id: 0, description: $("#newDesc").val()};
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/todolist/items',
                dataType: 'json',
                data: item,
            }).done(function(data) {
                let id = data.id;
                $("#itemsList").after(
                    "<div class='row'>" +
                        "<div class='col-8 col-md-5'>" +
                            "<input type='text' class='form-control' id='desc" + id + "' value='" + data.description + "'>" +
                        "</div>" +
                        "<div class='col-8 col-md-2'>" +
                            "<input type='text' class='form-control' id='day" + id + "' value='" + data.created + "' readonly>" +
                        "</div>" +
                        "<div class='col-8 col-md-1'>" +
                            "<input type='checkbox' class='form-check-input' id='status" + id + "'" + data.done + " onclick='updateItem("+id+")'>" +
                        "</div>" +
                    "</div>"
                )
            }).fail(function (err) {
                alert(err);
            });
        }

        function updateItem(id) {
            let descInput = document.getElementById("desc" + id);
            let status = document.getElementById("status" + id);
            let day = document.getElementById("day" + id);
            const item = {id: id, description: descInput.value, day: day.value, status: status.checked};
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/todolist/items',
                dataType: 'json',
                data: item,
            });
        }

        $(document).ready( function() {
            $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/todolist/items',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            }).done(function(data) {
                for(let i=0; i<data.length; i++) {
                    let id = data[i].id;
                    let description = data[i].description;
                    let day = data[i].created;
                    let status;
                    if (data[i].done) {
                        status=" checked";
                    }
                    $("#itemsList").after(
                        "<div class='row'>" +
                            "<div class='col-8 col-md-5'>" +
                                "<input type='text' class='form-control' id='desc" + id + "' value='" + description + "'>" +
                            "</div>" +
                            "<div class='col-8 col-md-2'>" +
                                "<input type='text' class='form-control' id='day" + id + "' value='" + day + "' readonly>" +
                            "</div>" +
                            "<div class='col-8 col-md-1'>" +
                                "<input type='checkbox' class='form-check-input' name='check' id='status" + id + "'" + status + " onclick='updateItem("+id+")'>" +
                            "</div>" +
                        "</div>"
                )
            }
        }).fail(function(err){
            alert(err);
        });
        })

        function hideDoneItem() {
            if ($('#switch').is(":checked")) {
                $('input:checkbox[name=check]:checked').each(function () {
                    $(this).closest($('.row')).hide();
                })
            }
            else {
                $('input:checkbox[name=check]:checked').each(function () {
                    $(this).closest($('.row')).show();
                })
            }
        }

    </script>
</head>
<body>
<div class="container">
    <div class="row pt-5">
        <div class="col-6">
            <h5>Новая задача</h5>
        </div>
    </div>
    <div class="row">
        <div class="col-8 col-md-7">
            <input type="text" class="form-control" id="newDesc" placeholder="Опишите задачу">
        </div>
        <div class="col-8 col-md-1 text-center">
            <button type="button" class="btn btn-success" onclick="addNewItem()">Добавить</button>
        </div>
    </div>
    <div class="row pt-5">
        <div class="col-8 col-md-3 form-check form-switch">
            <input class="form-check-input" type="checkbox" id="switch" onclick="hideDoneItem()">
            <label class="form-check-label" for="switch">Только незавершенные</label>
        </div>
    </div>
    <div class="row pb-3" id="itemsList">
        <div class="col-8 col-md-5">
            <h5>Описание задач</h5>
        </div>
        <div class="col-8 col-md-2">
            <h5>Создана</h5>
        </div>
        <div class="col-8 col-md-1">
            <h5>Статус</h5>
        </div>
    </div>
</div>
</body>
</html>
