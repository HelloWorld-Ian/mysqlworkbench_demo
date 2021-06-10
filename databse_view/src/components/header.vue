<template>
  <div>
    <div>
    <img src="../assets/logo.svg" style="height:50px;width:50px;margin-left:35px;margin-top:5px;float:left" />
    <h1 style="margin-left:20px;color: dodgerblue;margin-top:5px;float:left">数据库课程作业</h1>
    <div style="margin-left:400px">
  <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
  <el-menu-item index="1" @click="$router.push('/')" >建表</el-menu-item>
  <el-submenu index="2" style="margin-top:-1px">
    <template  #title>
    <span style="font-size:20px">查表</span>
    </template>
    <el-menu-item v-for="(item,i) in names" :key="i" :index="'2-'+i" @click="relocate(item)">{{item}}</el-menu-item>
  </el-submenu>
</el-menu>
    </div>
<div class="line"></div>
    </div>
    <el-divider style="margin-top:0px"></el-divider>
  </div>
</template>

<script>
export default {
  async created(){
    let that=this
    await this.axios.get('/post/controller/getTableName').then(function(res){
                that.names=res.data
            }).catch(function(error){
                alert(error)
            })
  },
   methods:{
     relocate(name){
       if(name!=null){
         this.$router.push({
           name:"display",
           params:{
             name:name
           }
         })
       }
     }
   },
   data(){
     return{
       names:[]
     }
   }
}
</script>

<style scoped>
.a{
  color:dodgerblue
}
</style>

<style scoped>
.el-menu-item{
  font-size:20px
}

.title{
  font-size:20px
}
</style>