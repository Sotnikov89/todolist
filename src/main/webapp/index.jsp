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
            const item = {id: 0, description: $("#newDesc").val(), categories: $('#newCat').val()};
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/todolist/items',
                dataType: 'json',
                data: item,
            }).done(function(data) {
                let created = data.created.time.hour + ":" + data.created.time.minute + " " + data.created.date.day + "-" + data.created.date.month;
                let categoryList = data.categories;
                let status;
                if (data.done) {
                    status=" checked";
                }
                $("#itemsList").after(
                    "<div class='row pb-2 gx-1'>" +
                        "<div class='col-10 col-md-6'>" +
                            "<input type='text' class='form-control' id='desc" + data.id + "' value='" + data.description + "' readonly>" +
                        "</div>" +
                        "<div class='col-10 col-md-1'>" +
                            "<select class='form-select' aria-label='multiple select example' id='cat" + data.id +  "' disabled>" +
                            "</select>" +
                        "</div>" +
                        "<div class='col-10 col-md-1'>" +
                            "<input type='text' class='form-control' id='day" + data.id + "' value='" + created + "' readonly>" +
                        "</div>" +
                        "<div class='col-10 col-md-1'>" +
                            "<input type='text' class='form-control' id='user" + data.id + "' value='" + data.user.name + "' readonly>" +
                        "</div>" +
                        "<div class='col-10 col-md-1 justify-content-center'>" +
                            "<input type='checkbox' class='btn-check'  name='check' id='status" + data.id + "'" + status + " onclick='updateItem("+data.id+")'>" +
                            "<label class='btn btn-primary' for='status" + data.id + "' id='lab" + data.id + "'>" + st + "</label>" +
                        "</div>" +
                    "</div>"
                )
                for (let y=0; y<categoryList.length; y++) {
                    let select = document.getElementById("cat" + data.id);
                    select.size = categoryList.length;
                    select.append(new Option(categoryList[y].name, categoryList[y].id))
                }
            }).fail(function (err) {
                alert(err);
            });
        }

        function updateItem(id) {
            let status = document.getElementById("status" + id);
            const item = {id: id, status: status.checked};
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/todolist/items',
                dataType: 'json',
                data: item,
            });
            if (status.checked) {
                document.getElementById("lab" + id).innerHTML = 'Выполнена';
            } else {
                document.getElementById("lab" + id).innerHTML = 'Активна';
            }
        }

        $(document).ready( function() {
            $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/todolist/items',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            }).done(function(data) {
                for(let i=0; i<data.length; i++) {
                    let created = data[i].created.time.hour + ":" + data[i].created.time.minute + " " + data[i].created.date.day + "-" + data[i].created.date.month;
                    let categoryList = data[i].categories;
                    let status;
                    let st = "Активна"
                    if (data[i].done) {
                        status=" checked";
                        st = "Выполнена";
                    }
                    $("#itemsList").after(
                        "<div class='row pb-2 gx-1'>" +
                            "<div class='col-10 col-md-6'>" +
                                "<input type='text' class='form-control' id='desc" + data[i].id + "' value='" + data[i].description + "' readonly>" +
                            "</div>" +
                            "<div class='col-10 col-md-1'>" +
                                "<select class='form-select' aria-label='multiple select example' id='cat" + data[i].id +  "' disabled>" +
                                "</select>" +
                            "</div>" +
                            "<div class='col-10 col-md-1'>" +
                                "<input type='text' class='form-control' id='day" + data[i].id + "' value='" + created + "' readonly>" +
                            "</div>" +
                            "<div class='col-10 col-md-1'>" +
                                "<input type='text' class='form-control' id='user" + data[i].id + "' value='" + data[i].user.name + "' readonly>" +
                            "</div>" +
                            "<div class='col-10 col-md-1 justify-content-center'>" +
                                "<input type='checkbox' class='btn-check'  name='check' id='status" + data[i].id + "'" + status + " onclick='updateItem("+data[i].id+")'>" +
                                "<label class='btn btn-primary' for='status" + data[i].id + "' id='lab" + data[i].id + "'>" + st + "</label>" +
                            "</div>" +
                        "</div>"
                    )
                    for (let y=0; y<categoryList.length; y++) {
                        let select = document.getElementById("cat" + data[i].id);
                        select.size = categoryList.length;
                        select.append(new Option(categoryList[y].name, categoryList[y].id))
                    }
                }
            }).fail(function(err){
                alert(err);
            });
        })
        $(document).ready( function() {
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/todolist/category',
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
            }).done(function(data) {
                for(let i=0; i<data.length; i++) {
                    $("#newCat").append(new Option(data[i].name, data[i].id))
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
    <div class="row pt-5 gx-1">
        <div class="col-10 col-md-8">
            <h5>Новая задача</h5>
        </div>
        <div class="col-10 col-md-2">
            <a class="nav-link" href="<%=request.getContextPath()%>/auth">
                <%=request.getSession().getAttribute("userName")%> -> Выйти</a>
        </div>
    </div>
    <div class="row gx-1">
        <div class="col-10 col-md-8">
            <input type="text" class="form-control" id="newDesc" placeholder="Опишите задачу">
        </div>
        <div class="col-10 col-md-1">
            <select class='form-select' id="newCat" size="1" multiple="multiple" aria-label='multiple select example'>
            </select>
        </div>
        <div class="col-10 col-md-1 text-center">
            <button type="button" class="btn btn-success" onclick="addNewItem()">Добавить</button>
        </div>
    </div>
    <div class="row pt-5">
        <div class="col-10 col-md-3 form-check form-switch">
            <input class="form-check-input" type="checkbox" id="switch" onclick="hideDoneItem()">
            <label class="form-check-label" for="switch">Только незавершенные</label>
        </div>
    </div>
    <div class="row pb-3" id="itemsList">
        <div class="col-10 col-md-6">
            <h5>Описание задач</h5>
        </div>
        <div class="col-10 col-md-1">
            <h5>Категория</h5>
        </div>
        <div class="col-10 col-md-1">
            <h5>Создана</h5>
        </div>
        <div class="col-10 col-md-1">
            <h5>Автор</h5>
        </div>
        <div class="col-10 col-md-1">
            <h5>Статус</h5>
        </div>
    </div>
</div>
</body>
</html>