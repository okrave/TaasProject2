window.onload = _ =>{

    app = new Vue({
        el:"#appGroupEsplora",
        data: {
            toGroupAPI : new ToGroup(),
            userLogged: new UserLogged(),
            typeSearch : "cards"
        },
        mounted(){
            this.userLogged.reloadUserInfo();
        },

        created(){
            this.removeLoader();
        },

        computed: {
            getTypeSearch:function () {
                return this.typeSearch;
            }

         },

        methods:{
            removeLoader(){
                document.getElementById('loaderCustom').style.visibility = 'hidden';
                document.getElementById('appGroupEsplora').style.visibility = 'visible';
            }
        }

    });
};