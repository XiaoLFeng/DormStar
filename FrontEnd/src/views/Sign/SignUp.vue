<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <img alt="Your Company" class="mx-auto h-10 w-auto"
           src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=600"/>
      <h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">
        DormStar - 注册
      </h2>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <form action="#" class="space-y-6" method="POST">
        <div>
          <label class="block text-sm font-medium leading-6 text-gray-900" for="email">邮箱</label>
          <div class="mt-2">
            <input id="user" autocomplete="user"
                   class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                   name="user" required=""
                   type="text"/>
          </div>
        </div>
        <div>
          <label class="block text-sm font-medium leading-6 text-gray-900" for="email">昵称</label>
          <div class="mt-2">
            <input id="user" autocomplete="user"
                   class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                   name="user" required=""
                   type="text"/>
          </div>
        </div>
        <div>
          <label class="block text-sm font-medium leading-6 text-gray-900" for="email">电话</label>
          <div class="mt-2">
            <input id="user" autocomplete="user"
                   class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                   name="user" required=""
                   type="text"/>
          </div>
        </div>
        <div>
          <div class="flex items-center justify-between">
            <label class="block text-sm font-medium leading-6 text-gray-900" for="password">密码</label>
            <div class="text-sm">
              <a class="font-semibold text-indigo-600 hover:text-indigo-500" href="/sign/reset">忘记密码</a>
            </div>
          </div>
          <div class="mt-2">
            <input id="password" autocomplete="current-password"
                   class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                   name="password" required=""
                   type="password"/>
          </div>
        </div>

        <div>
          <button
              class="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
              type="submit">
            注册
          </button>
        </div>
      </form>

      <p class="mt-10 text-center text-sm text-gray-500">
        我已经有账号了
        <a class="font-semibold leading-6 text-indigo-600 hover:text-indigo-500" href="/sign/in">登陆！</a>
      </p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      data: [], // 存储从接口获取的数据
    };
  },
  mounted() {
    // 在组件挂载后执行获取数据的操作
    this.getDataFromApi();
  },
  methods: {
    getDataFromApi() {
      // 使用 Axios 发送 GET 请求获取 JSON 接口数据
      axios.get('http://localhost:8080/api/user/sign/in')
          .then(response => {
            // 成功获取数据后更新组件的数据
            this.data = response.data;
            if (this.data.output === "SuccessCreate") {
              // 设置Cookie的过期时间为12小时
              const expireDate = new Date();
              expireDate.setTime(expireDate.getTime() + 43200000);
              // 使用document.cookie创建Cookie
              document.cookie = `session=${this.data.data}; expires=${expireDate.toUTCString()}; path=/`;
            }
          })
          .catch(error => {
            // 处理错误
            console.error('Error fetching data:', error);
          });
    },
  },
};
</script>