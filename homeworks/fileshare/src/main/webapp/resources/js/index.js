function showFolder(id){
    console.log("showFolder id:" + id);
    $.ajax({
        url: '127.0.0.1:8080/?parentId=' + id,
        type: 'GET',
    });
}

function createFolder(parentId){
    let name = document.getElementById("innerFolderName_" + parentId).value;

    console.log("createFolder id:" + id + "name:" + name);
    $.ajax({
        url: '127.0.0.1:8080/folder/create',
        type: 'POST',
        data: {name: name , parentId: parentId},

    });
}



function updateFolderName(id){
    const name = document.getElementById("folderName_" + id).value;

    console.log("updateFolderName id:" + id + "newName:" + name);
    $.ajax({
        url: '127.0.0.1:8080/folder/update',
        type: 'PUT',
        data: {id: id, newName: name},
        success: function (result) {
            console.log("updateFolderName success")
        }
    });

}

function updateFolderParent(id){
    const parentId = document.getElementById("folderParent_" + id).value;
    console.log("updateFolderParent id:" + id + "parentId:" + parentId);

    $.ajax({
        url: '127.0.0.1:8080/folder/update',
        type: 'PUT',
        data: {id: id, newParentId: parentId},
        success: function (result) {
            console.log("updateFolderParent success")
        }
    });

}
function deleteFolder(id){
    console.log("deleteFolder id:" + id);
    $.ajax({
        url: '127.0.0.1:8080/folder/delete',
        type: 'DELETE',
        data: {id: id},
        success: function (result) {
            console.log("deleteFolder success")
        }
    });
}

function createLink(id){
    console.log("createLink id:" + id);
    $.ajax({
        url: '127.0.0.1:8080/link/create',
        data: {id: id}
    });
}

function deleteLink(){

    const generatedName = document.getElementById("linkDelete").value;

    console.log("deleteLink generatedName:" + generatedName);
    $.ajax({
        url: '127.0.0.1:8080/link/delete',
        data: {generatedName: generatedName},
    });
}
