(function ($) {
        let submitAction = function() {
            /* do something with Error */
            return false;
        };
        $('form').bind('submit', submitAction);

        let regex = new RegExp("(.*?)\.(jpeg|jpg|png)$");
        let maxSize = 5242800;

        function checkExtension(fileName, fileSize) {
            if (fileSize >= maxSize) {
                alert('파일 사이즈가 초과되었습니다.');
                return false;
            }

            if (!regex.test(fileName)) {
                alert("해당 종류의 파일은 업로드 할 수 없습니다.");
                return  ;
            }

            return true;
        }

        let uploadResult = $(".uploadResult ul");

        function showUploadFile(uploadResultArr) {
            let str = "";
            $(uploadResultArr).each(function (i, obj) {
                let fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
                str += `<li data-path=${obj.uploadPath} data-uuid=${obj.uuid} data-fileName=${obj.fileName}><img src='/display?fileName=${fileCallPath}'>`;
                str += `<span data-file=${fileCallPath}>x</span></li>`;
            });

            uploadResult.append(str);
        }

        $("input[type='file']").change(function (e) {
            let formData = new FormData();
            let inputFile = $("input[name='uploadFile']");
            let files = inputFile[0].files;

            for (let i = 0; i < files.length; i++) {
                if (!checkExtension(files[i].name, files[i].size)) {
                    return false;
                }
                formData.append("uploadFile", files[i]);
            }

            $.ajax({
                url: '/uploadAjaxAction',
                processData: false,
                contentType: false,
                data: formData,
                type: 'POST',
                success: function (result) {
                    showUploadFile(result);
                }
            });

        });

        $(".uploadResult").on("click", "span", function (e) {
            let target = $(this).data("file");
            let targetLi = $(this).closest("li");

            $.ajax({
                url : '/deleteFile',
                data: {fileName: target},
                dataType: 'text',
                type: 'POST',
                success: function (result) {
                    alert(result);
                    targetLi.remove();
                }
            });
        })

        $("button[type='button']").on("click", function(e){

            let data;
            let attachList = [];

            $(".uploadResult ul li").each(function (i, obj) {
                let jobj = $(obj);
                attachList.push({"fileName": jobj.data("filename"), "uuid" : jobj.data("uuid"), "uploadPath": jobj.data("path")})
                console.log(attachList);
            });

            data = {
                name : $("#name").val(),
                price : $("#price").val(),
                description : $("#description").val(),
                categoryId : $("#categoryId").val(),
                attachList : attachList
            };

            $.ajax({
                url : '/api/goods/new',
                data: JSON.stringify(data),
                contentType: "application/json",
                dataType: 'text',
                type: 'POST',
                success: function (result) {
                    alert("저장되었습니다.");
                    location.href="/";
                }
            });
        });
})(jQuery);