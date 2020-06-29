$(function () {//页面加载完成
    $("#loginfrom").bootstrapValidator({
        // 默认的提示消息
        message: "this is not valid field",
        // 表单框里右侧的icon
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username:{
                validators: {
                    notEmpty: {
                        message: "用户名不能为空！"
                    },
                }
            },
            password: { // name="password" 密码不能与账户相同
                validators: {
                    notEmpty: {
                        message: "密码不能为空！"
                    },
                    regexp: {
                        message: "密码由小写字母、数字组成,长度应在6到10之间！",
                        regexp: /^[a-z0-9]{6,10}$/
                    },
                }
            },
        }
    })

    var message = $("#mes").text();
    console.log(message);
    if(message !=""){
        $(".form-control").focus(function(){
            $("#mes").addClass("none");
        });
    }
});