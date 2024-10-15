import axios from "axios";
export default {
    state: {
        id: '',
        username: '',
        photo: '',
        token: '',
        is_login: false
    },
    getters: {
    },
    mutations: {
        updateUser(state,user){
            state.id=user.id;
            state.username=user.username;
            state.photo=user.photo;
            state.is_login=user.is_login;
        },
        updateToken(state,token){
            state.token=token;
        }
    },
    actions: {
        login(context,data){
            axios.post("/user/account/token",{
                username: data.username,
                password: data.password,
            }).then( resp =>{
                    context.commit("updateToken",resp.data.data.token);
                    data.success(resp);
            }).catch(error =>{
                if(error.status=== 403) {
                    data.error({errorMessage: '用户名或密码错误'});
                }else{
                    alert('服务器内部错误');
                }
            });
        },
        getInfo(context,data){
            axios.get('/user/account/info',{
                headers:{
                    Authorization: 'Bearer ' + context.state.token
                }
            }).then(resp => {
                context.commit("updateUser",{
                    ...(resp.data.data),
                    is_login: true
                });
                console.log(JSON.stringify(resp))
                data.success(resp);
            }).catch(error => {
                data.error(error);
            })
        },
        logout(context,data){
            context.commit('updateToken','');
            context.commit('updateUser',{
                username: '',
                id: '',
                photo: '',
                is_login: false
            })
            data.success();
        }
    },
    modules: {
    }
}