let main = {
    init : function () {
        let _this = this;
        $("#loginButton").on("click", function (){
            console.log("# loginButton")
            _this.login();
        });

        $("#logoutButton").on("click", function (){
            console.log("# logoutButton")
            _this.logout();
        })

        $("#signup").on("click", function (){
            console.log("# signupButton")
            _this.signup();
        })

    },

    signup : function () {
        let userInfo = {
            email : $("#email").val(),
            password : $("#password").val(),
            name : $("#name").val(),
            tel : $("#tel").val(),
            addr : $("#addr").val(),
            birth : $("#birth").val(),
            account : $("#account").val(),
        }

        fetch('/api/signup', {
            method: 'post',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userInfo),
        })
            .then(response => response.json())
            .then(response => {
                if (response) {
                    alert("회원가입 되었습니다.");
                    window.location.href = "/";
                }
            })
            .catch(error => console.error('Error: ', error));
    },

    logout : function () {
      fetch("/api/logout", {
          method: 'get',
      })
          .then(res => console.log(res))
          .then(res => {
              console.log("# res" + res)
              window.location.href = "/";
          })
          .catch(error => console.error('Error: ', error));
    },

    login : function () {
        let userInfo = {
            username : $("#username").val(),
            password : $("#password").val()
        }

        fetch('/api/authenticate', {
            method: 'post',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userInfo),
        })
            .then(response => response.json())
            .then(response => {
                if (response.token) {
                    alert("로그인 성공!");
                    window.location.href="/";
                }
            })
            .catch(error => console.error('Error: ', error));
    },
};

main.init();
