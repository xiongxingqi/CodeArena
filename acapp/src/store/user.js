import axios from "axios";
export default {
    state: {
        id: '',
        username: '',
        photo: '',
        token: '',
        is_login: false,
        accessToInformation: true
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
        },
        updateAccessToInformation(state,update){
            state.accessToInformation=update;
        }
    },
    actions: {
        login(context,data){
            axios.post("/user/account/token",{
                username: data.username,
                password: data.password,
            }).then( resp =>{
                const res = resp.data;
                if(res.code === 1){
                    console.log(res.data.token)
                    localStorage.setItem("token", res.data.token);
                    context.commit("updateToken",res.data.token);
                    data.success();
                }else {
                    data.error(res);
                }
            }).catch(error =>{
                alert("服务出错,响应码为:" + error.status);
            });
        },
        getInfo(context,data){
            axios.get('/user/account/info',{
                headers:{
                    Authorization: 'Bearer ' + context.state.token
                }
            }).then(resp => {
                // console.log(JSON.stringify(resp))
                let res = resp.data;
                if(res.code === 1){
                    data.success(resp.data);
                }else {
                    alert(res.message);
                }
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