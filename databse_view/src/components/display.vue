<template>
  <el-table
    :data="tableData"
    style="width: 100%">
    <el-table-column v-for="(item,i) in fields" :key="i" 
      :prop="item"
      :label="item"
      width="150">
       <template #default="scope">
          <span v-if="scope.row.show" >{{scope.row[item]}}</span>
          <el-input v-if="!scope.row.show" v-model="scope.row[item]"> </el-input>
       </template>
    </el-table-column>
    <el-table-column 
      fixed="right"
      width="110">
      <template #header>
        <img src="../assets/add.svg" v-if="!searchMode" style="width:20px;height:20px;margin-bottom:-5px" @click="add" />
        <el-button
          size="mini" v-if="!searchMode" style="margin-bottom:5px;margin-left:5px" @click="search">查询</el-button>
          <el-button
          size="mini" v-if="searchMode" @click="back" style="margin-bottom:5px;margin-left:5px">还原</el-button>
      </template>
      <template #default="scope">
      <el-button
          size="mini"
          @click="getSearchRes(scope.$index)" v-if="scope.row.search" style="margin-bottom:5px">查询</el-button>
        <el-button
          size="mini"
          @click="Save(scope.$index,scope)" v-if="!scope.row.show&&!scope.row.search" style="margin-bottom:5px">保存</el-button>
        <el-button
          size="mini"
          @click="scope.row.show=false" v-if="scope.row.show&&!scope.row.search" style="margin-bottom:5px">编辑</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="Delete(scope.$index)" style="margin-left:0px" >删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
  export default {
    async created(){
      let params={
        name:this.$route.params.name
      }
      let that=this
      await this.axios.post('/post/controller/getTableField',this.$qs.stringify(params)).then(function(res){
        that.fields=JSON.parse(res.data)
      })
      await this.axios.post('/post/controller/getData',this.$qs.stringify(params)).then(function(res){
        res.data.forEach(function(item){
          let empty='{'
          that.fields.forEach(function(field,i){
          empty+="\""+field+"\":"+"\""+item[i]+"\","
        })
        empty+="\""+"show"+"\":"+"true,"
        empty=empty.substr(0,empty.length-1)
        empty+='}'
        empty=JSON.parse(empty)
        that.tableData.push(empty)
        })
      })
    },
    methods: {
      back(){
        this.searchMode=false
        this.tableData=[]
         let params={
        name:this.$route.params.name
      }
        let that=this
        this.axios.post('/post/controller/getData',this.$qs.stringify(params)).then(function(res){
        res.data.forEach(function(item){
          let empty='{'
          that.fields.forEach(function(field,i){
          empty+="\""+field+"\":"+"\""+item[i]+"\","
        })
        empty+="\""+"show"+"\":"+"true,"
        empty=empty.substr(0,empty.length-1)
        empty+='}'
        empty=JSON.parse(empty)
        that.tableData.push(empty)
        })
      })
      },
      getSearchRes(index){
        let fields=this.fields
        let newTableData=[]
        let searchData=this.tableData[index]
        this.tableData.splice(index,1)
        this.tableData.forEach(function(item){
           let get=true
           fields.forEach(function(field){
             if(searchData[field]!=''){
               if(searchData[field]!=item[field]){
                 get=false
               }
             }
           })
           if(get==true){
             newTableData.push(item)
           }
        })
        this.tableData=newTableData
      },
      search(){
        let empty='{'
        this.fields.forEach(function(item){
        empty+="\""+item+"\":"+"\"\","
        })
        empty+="\""+"show"+"\":"+"false,"
        empty+="\""+"search"+"\":"+"true,"
        empty=empty.substr(0,empty.length-1)
        empty+='}'
        empty=JSON.parse(empty)
        this.tableData.unshift(empty)
        this.searchMode=true
      },
      add(){
        let empty='{'
        this.fields.forEach(function(item){
          empty+="\""+item+"\":"+"\"\","
        })
        empty+="\""+"show"+"\":"+"false,"
        empty+="\""+"search"+"\":"+"false,"
        empty=empty.substr(0,empty.length-1)
        empty+='}'
        empty=JSON.parse(empty)
        this.tableData.push(empty)
      },
      Delete(index){
         let params={
          table_name:this.$route.params.name,
          id:parseInt(this.tableData[index].id)
        }
        this.axios.post('/post/controller/deleteData',this.$qs.stringify(params)).then(function(res){
         if(res.data==="success"){
           alert("删除成功")
         }else{
           alert("删除失败")
         }
      })
       this.tableData.splice(index,1)
      },
      async Save(index,scope){
        let params={
          name:this.$route.params.name,
          data:JSON.stringify(this.tableData[index])
        }
        let params_2={
          table_name:this.$route.params.name,
          id:parseInt(this.tableData[index].id)
        }
        await this.axios.post('/post/controller/deleteData',this.$qs.stringify(params_2)).then(function(res){
         if(res.data!=="success"){
           alert("删除失败")
         }
         })
        await this.axios.post('/post/controller/saveData',this.$qs.stringify(params)).then(function(res){
         if(res.data==="success"){
           alert("保存成功")
         }else{
           alert("保存失败")
         }
      }).catch(function(error){
        alert(error)
      })
        scope.row.show=true
      }
    },
    
    data() {
      return {
        fields:[],
        tableData: [],
        searchMode:false
      }
    }
  }
</script>

<style scoped>
.el-card{
    margin-left:50px;
    width: 600px;
    margin-right:80px
}
img:hover{
    cursor: pointer;
}

body .el-table th.gutter {
    display: table-cell !important
}

.el-table {
  overflow-y: auto !important;
}

</style>