<template >
  <div class="container">
    <div class="row" >
      <div class="col-md-3">
        <div class="card " style="margin-top: 20px ">
          <div class="card-body">
            <img :src="$store.state.user.photo" alt="头像" class="photo">
          </div>
        </div>
      </div>
      <div class="col-md-9">
        <div class="card" style="margin-top: 20px">
          <div class="card-header">
            <span style="font-size: 130%">我的BOT</span>
            <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal" data-bs-target="#add-bot">创建BOT</button>

            <!-- Modal -->
            <div class="modal fade "  id="add-bot" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
              <div class="modal-dialog modal-lg" >
                <div class="modal-content" >
                  <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">创建BTO</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <div class="input-group mb-3">
                      <span class="input-group-text" id="basic-addon1">名称</span>
                      <input v-model="botDto.title" type="text" class="form-control" placeholder="请输入名称" aria-label="Username" aria-describedby="basic-addon1" >
                    </div>
                    <div class="input-group mb-3">
                      <span class="input-group-text" id="basic-addon2">简介</span>
                      <textarea  v-model="botDto.description" type="text" class="form-control" placeholder="请输入简介" rows="3" aria-label="Username" aria-describedby="basic-addon2"/>
                    </div>
                    <div class="mb-3">
                      <label for="add-bot-code" class="form-label">代码</label>
                        <VAceEditor
                            id="add-bot-code"
                            v-model:value="botDto.content"
                            @init="editorInit"
                            lang="c_cpp"
                            theme="textmate"
                            style="height: 300px" />
                    </div>

                  </div>
                  <div class="modal-footer">
                    <div class="error-message">{{botDto.error_message}}</div>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                    <button type="button" @click="add_bot" class="btn btn-primary">创建</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="card-body">
            <table class="table table-striped table-hover">
              <thead>
                <tr>
                  <th>标题</th>
                  <th>简介</th>
                  <th>创建时间</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="bot in bots" :key="bot.id">
                  <td>{{bot.title}}</td>
                  <td>{{bot.description}}</td>
                  <td>{{bot.createTime}}</td>
                  <td>
                    <button type="button" class="btn btn-primary add-btn" data-bs-toggle="modal" :data-bs-target="'#update-bot'+bot.id">修改</button>
                    <button type="button" class="btn btn-danger" @click="remove_bot(bot)">删除</button>

                    <!-- Modal -->
                    <div class="modal fade "  :id="'update-bot' + bot.id" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                      <div class="modal-dialog modal-lg" >
                        <div class="modal-content" >
                          <div class="modal-header">
                            <h1 class="modal-title fs-5" :id="'staticBackdropLabel-update-'+ bot.id">修改BTO</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" @click="refresh"></button>
                          </div>
                          <div class="modal-body">
                            <div class="input-group mb-3">
                              <span class="input-group-text" :id="'basic-update1'+bot.id">名称</span>
                              <input v-model="bot.title" type="text" class="form-control" placeholder="请输入名称" aria-label="Username" :aria-describedby="'basic-update1'+bot.id" >
                            </div>
                            <div class="input-group mb-3">
                              <span class="input-group-text" :id="'basic-update2'+bot.id">简介</span>
                              <textarea  v-model="bot.description" type="text" class="form-control" placeholder="请输入简介" rows="3" aria-label="Username" :aria-describedby="'basic-update2'+ bot.id"/>
                            </div>
                            <div class="mb-3">
                              <label :for="'update-bot-code'+bot.id" class="form-label">代码</label>
                              <VAceEditor
                                  :id="'update-bot-code'+bot.id"
                                  v-model:value="bot.content"
                                  @init="editorInit"
                                  lang="c_cpp"
                                  theme="textmate"
                                  style="height: 300px" />
                            </div>

                          </div>
                          <div class="modal-footer">
                            <div class="error-message">{{messageError}}</div>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="refresh">取消</button>
                            <button type="button" @click="update_bot(bot)" class="btn btn-primary">保存</button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import 'jquery/dist/jquery.min'
import {useStore} from "vuex";
import {reactive, ref} from "vue";
import { VAceEditor } from 'vue3-ace-editor';
import ace from 'ace-builds';
import 'ace-builds/src-noconflict/mode-c_cpp';
import {Modal} from "bootstrap/dist/js/bootstrap";

export default {
  name: 'UserBotIndexView',
  components: {
    VAceEditor,
  },
  setup(){
    const bots = ref([]);
    const store = useStore();
    const botDto = reactive({
      title: '',
      description: '',
      content: '',
      error_message:''
    });
    const messageError = ref('');
    ace.config.set(
        "basePath",
        "https://cdn.jsdelivr.net/npm/ace-builds@" + require('ace-builds').version + "/src-noconflict/");
    const refresh=() =>{
      axios.get('/user/bot/getList',{
        headers: {
          Authorization: 'Bearer ' + store.state.user.token
        }
      }).then((resp)=>{
        const result = resp.data;
        if(result.code === 1){
          bots.value=result.data;
        }else{
          alert(result.message);
        }
      }).catch((error)=>{
        alert("网络环境波动,请稍后再试"+error.status);
      });
    }
    const add_bot=()=>{
      axios.post('/user/bot/addBot',{
        title: botDto.title,
        description: botDto.description,
        content: botDto.content
      },{
        headers: {
          Authorization: 'Bearer ' + store.state.user.token
      }
      }).then((resp)=>{
        let result = resp.data;
        if(result.code=== 1){
          botDto.title='';
          botDto.description='';
          botDto.content='';
          Modal.getInstance("#add-bot").hide();
          refresh();
        }else {
          botDto.error_message=result.message;
        }
      }).catch((error)=>{
        alert("添加失败服务器异常:"+error.status);
      });
    }
    const remove_bot=(bot)=>{
      axios.delete('/user/bot/remove?id='+bot.id,{
        headers:{
          Authorization: 'Bearer ' + store.state.user.token
        }
      }).then((resp)=>{
        const result = resp.data;
        if(result.code === 1) {
          refresh();
        }else {
          alert(result.message);
        }
      }).catch(()=>{
        alert("网络异常,删除失败");
      });
    }

    const update_bot= (bot)=>{
      axios.post('/user/bot/update',{
        ...bot
      },{
        headers:{
          Authorization: 'Bearer ' + store.state.user.token
      }
      }).then((resp)=>{
        messageError.value='';
        const result = resp.data;
        if(result.code === 1){
          Modal.getInstance("#update-bot"+bot.id).hide();
          refresh();
        }else {
          messageError.value=result.message;
        }
      }).catch((error)=>{
        alert('网络异常,保存失败'+error.status);
      });
    }

    refresh();

    return {
      bots,
      botDto,
      add_bot,
      remove_bot,
      update_bot,
      messageError,
      refresh
    }
  }

}
</script>
<style scoped>
.error-message{
  color: red;
}
.photo{
  width: 100%;
}
.add-btn{
  margin-right: 10px;
}
</style>