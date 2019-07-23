window.onload = _ => {

    $(document).ready(attachClicksToModalButtonsLoginRegister);

    app = new Vue({
        el:"#appUserPageJ2ee",
        data:{
            resultJ2ee : ""

        },
        mounted(){

        },

        created(){
            this.loadPage();
            this.removeLoader();
        },

        computed: {
            isLogged(){
                return (this.userLogged != null && this.userLogged  !== undefined) ? this.userLogged.isLogged : false;
            }
        },

        methods:{
            removeLoader(){
                document.getElementById('loaderCustom').style.visibility = 'hidden';
                document.getElementById('appUserPageJ2ee').style.visibility = 'visible';
            },

            loadPage(){
                var resultElement = document.getElementById('j2ee_page');
                resultElement.innerHTML = '';

                var p =  new Promise((resolve, reject) => {
                        fetch("http://localhost:8081/J2EEToGroup-web/NewServlet?id=5", {
                        method: "GET",
                        mode: 'no-cors',
                        credentials: "include"
                    })

            .then(response => {
                        //resultElement.innerHTML = response;
                            console.log("Prima" + JSON.stringify(response.text()));
                            if (checkResponseHoldsErrors(response)) {
                                reject(response);
                            }
                            resolve(response);
                        }).catch(reject);
                    });

                    p.then(resp => {
                        this.resultJ2ee = resp;
                        console.log(JSON.stringify(resp));
                    });

            }
        }
    });

}