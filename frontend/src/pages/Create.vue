<template>
<head>
  <link
    rel="stylesheet"
    href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
    integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
    crossorigin="anonymous"
  />
</head>
<header>
  <div >
    <div style="display:flex; align-items:center;" class="top">
      <i class="fas fa-arrow-left " @click="goHome" style="background:white; color:#3B5E8C; margin:0px 4.8rem 0px 20px;"></i>
      <img class="logo" src="../assets/horizon_logo.png">
      <!-- <q-btn class="back_btn" @click="goHome" dense round icon="edit" /> -->
    </div>
  </div>
</header>
  <div style="padding-bottom:100px; ">
    <div style="width:90%; margin-left:20px; margin-top:12px; margin-bottom:20px;">
      <div class="text-subitle2" style="height:400px; margin-bottom:20px;">
        <div :style="{ 'background-image': `url(${imageData})` }" @click="choosepicture" style="object-fit:cover;">
          <q-btn
            v-if="!imageData"
            class="placeholder"
            style="width:100%; height:400px;"
            icon="photo_camera"
          />
          <input hidden class="file-input" ref="fileInput" type="file" @input="onSelectFile()" />
        </div>
        <q-img :src="imageData" style="cursor: pointer; object-fit:cover; height:400px;" @click="choosepicture()" />
      </div>
    </div>
    <div style="margin-top:4  0px;">
      <div class="q-pa-md" style="width:100%;">
        <q-input
          v-model="text"
          outlined
          label = "내용을 입력해주세요."
          type="textarea"
          color = blue
        />
      </div>
    </div>
    <div style="width:90%; margin-left:20px;">
      <span>해시태그</span>
      <q-input v-model="hashtag" label="ex)#육아#미역국"/>
    </div>
    <div style="display:flex; justify-content: center; margin-top:40px;">
      <q-btn @click="goWrite" unelevated rounded label="게시글 작성" style="width:300px; color:white; background:rgb(235,137,181);"/>
    </div>
  </div>
</template>

<script>
export default {
  el: '#q-app',
  data() {
    return {
      imageData: null,
      text: '',
      hashtag: '',
      img: '',
    }
  },
  methods: {
    onSelectFile() {
      const input = this.$refs.fileInput;
      const files = input.files;
      const FileImage = files[0];
      this.img = FileImage
      if (files && files[0]) {
        const reader = new FileReader();
        reader.onload = e => {
          this.imageData = e.target.result;
        };
        reader.readAsDataURL(files[0]);
        this.$emit("input", files[0]);
      }
    },
    choosepicture() {
      this.$refs.fileInput.click();
    },
    goHome(){
      this.$router.push('/home')
    },
    goWrite(){
      if (this.text == '' || this.hashtag==''){
          const Swal = require('sweetalert2')
          Swal.fire({
                  icon: 'error',
                  title: '<span style="font-size:25px;">내용을 전부 작성해주세요.</span>',
                  confirmButtonColor: '#primary',
                  confirmButtonText: '<span style="font-size:18px;">확인</span>'
              })
              pwdMode.value = false
        return
      }
      const hashlist = this.hashtag.split('#').slice(1,)
      const article = {
        content:this.text,
        hashtag:hashlist,
        img:this.img,
        user_id:localStorage.getItem('userId')
      }
      this.$store.dispatch('module/writeArticle',article)
        .then(()=>{
          this.$store.dispatch('module/allArticle',localStorage.getItem('userId')).then((res)=>{
            this.$store.commit('module/setAllarticle', res.data)
            this.$router.push('/home')
          })
        })
    }
  },
}
</script>

<style scoped>
.top{
  display: flex;
  justify-content: flex-start;
  padding: none;
  /* padding-bottom: 1rem; */
  width: 110px;
  margin-top: 5px;
  align-items: center;
  /* width:100%; */
}
.logo{
  height: 70px;
  /* margin-top: 5px; */
  /* margin:0px 55px 0px 55px; */
}
.back_btn{
  display: flex;
  justify-content: flex-start;
  padding: none;
  /* padding-bottom: 1rem; */
  width: 130px;
  margin-top: 5px;
  align-items: center;
  /* background:rgb(235, 137, 181);
  color:white;
  width:10px;
  height:10px;
  margin:0px 0px 0px 0px; */
}

.top i{
  font-size: 1.2rem;
  margin-left: 1.5rem;
  padding-bottom: 0.3rem;
  color: #5684BF;
}

.show_img{
  width:90%;
}

</style>
