<template >
  <div class="container rankListContainer">
    <div class="card">
      <div class="card-header">
        <span class="rankFont">
          排行榜
        </span>
        <button type="button" class="btn btn-primary" style="float: right" @click="pull_page(currentPage)">刷新排行榜</button>
      </div>
      <div class="card-body">
        <table class="table table-hover" style='text-align: center;'>
          <thead>
          <tr>
            <th scope="col">
              玩家
            </th>
            <th scope="col">
              天梯分
            </th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="user in users" :key="user.id" >
            <td >
              <img :src="user.photo" alt=""/>
              &nbsp;
              <span>
                {{user.username}}
              </span>
            </td>
            <td >
              {{user.rating}}
            </td>
          </tr>
          </tbody>
        </table>
        <nav aria-label="Page navigation example">
          <ul class="pagination justify-content-end">
            <li class="page-item" @click="next_page(-1)"><a class="page-link" href="#">上一页</a></li>
            <li @click="pull_page(page.number)" :class="'page-item '+ page.active" v-for="page in pages" :key="page.number"><a class="page-link" href="#">{{page.number}}</a></li>
            <li class="page-item" @click="next_page(-2)"><a class="page-link" href="#">下一页</a></li>
          </ul>
        </nav>
      </div>
    </div>
  </div>

</template>
<script>
import {ref} from "vue";
import axios from "axios";
import {useStore} from "vuex";

export default {
    name: 'RankListIndexView',
    components: {
    },
    setup(){

      const users = ref([]);
      const currentPage = ref(1);

      const store = useStore();

      const pages = ref([]);
      let usertotal=0;

      const next_page = (ops) =>{
        let new_page =currentPage.value;
        if(ops === -1 )  new_page = currentPage.value-1;
        else if(ops === -2) new_page = currentPage.value+1;
        if(new_page>0&&new_page<=Math.ceil(usertotal/3))
          pull_page(new_page);
      }

      const update_page = (page) =>{
        let maxPage = Math.ceil(usertotal/3);
        pages.value=[];
        for(let i =page-2;i<=page+2;i++){
          if(i>0&&i<=maxPage){
            if(i === page){
              pages.value.push({
                active: "active",
                number: i
              })
            }else{
              pages.value.push({
                active: "",
                number: i,
              })
            }
          }
        }
      }
      const pull_page =(page)=>{

        currentPage.value = page;

        axios.get("/rank/getPageList",{
          params:{
            page,
          },
          headers:{
            Authorization: "Bearer " + store.state.user.token,
          }
        }).then((resp)=>{
          const result = resp.data;
          if(result.code === 1){
            usertotal = result.data.playerTotal;
            users.value = result.data.players;
            update_page(page)
          }else if(result.code === 2){
            alert(result.message);
          }
        }).catch((error) =>{
          console.log(error.status);
        })
      }

      pull_page(currentPage.value)

      return {
        users,
        currentPage,
        pages,
        pull_page,
        update_page,
        next_page
      }

    }
}
</script>
<style scoped>

div.rankListContainer{
  margin-top: 20px;

}

span.rankFont {
  font-size: 20px;
  font-weight: 600;
}
td > img {
  height: 4vh;
  border-radius: 50%;
}


</style>