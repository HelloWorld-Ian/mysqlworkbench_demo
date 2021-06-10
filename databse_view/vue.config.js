module.exports = {
    //相当于webpack-dev-server,  对本地服务器进行配置
    devServer : {
         proxy:  {
            "/post" : {
                 target: "http://localhost",
                 changeOrigin: true,        
                 pathRewrite: {
                     "^/post" : ""   
                 }    
            }
        }
    }     
}
// module.exports = {
//     outputDir: 'cishan',
//     assetsDir:'static',
//     publicPath:'./',
//   }