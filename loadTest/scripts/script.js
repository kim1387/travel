import http from 'k6/http';
import { check, sleep } from "k6";

const isNumeric = (value) => /^\d+$/.test(value);

const default_vus = 5;

const target_vus_env = `${__ENV.TARGET_VUS}`;
const target_vus = isNumeric(target_vus_env) ? Number(target_vus_env) : default_vus;

export let options = {
  stages: [
      // Ramp-up from 1 to TARGET_VUS virtual users (VUs) in 5s
      { duration: "5s", target: target_vus },

      // Stay at rest on TARGET_VUS VUs for 10s
      { duration: "10s", target: target_vus },

      // Ramp-down from TARGET_VUS to 0 VUs for 5s
      { duration: "5s", target: 0 }
  ]
};

export default function () {
  const response = http.get("https://swapi.dev/api/people/30/", {headers: {Accepts: "application/json"}});
  check(response, { "status is 200": (r) => r.status === 200 });
  sleep(.300);
};
import http from 'k6/http';
import { sleep } from 'k6';
import { FormData } from 'https://jslib.k6.io/formdata/0.0.2/index.js';

export const options = {
    stages: [
        { duration: '30s' , target: 30},
        { duration: '20s' , target: 10},
        { duration: '10s' , target: 5},
    ],
};

//random api start
let randomNumber = Math.floor(Math.random() * 4) + 1;
const img1 = open('monitor/testImg/5.jpg', 'b');
export default function () {
    sleep(1);
    switch (randomNumber) {
        case 1:
            console.log("elastic test");
            elastic_api ();
            break;
        case 2:
            console.log("fruit test");
            fruit_api ();
            break;
        case 3:
            console.log("email test");
            email_api ();
            break;
        case 4:
            console.log("upload_api");
            const fd = new FormData();
            fd.append('images', http.file(img1, '5.jpg', 'image/jpeg'));
            const res = http.post('http://svheyapple.com/api/v1/orders/tasks', fd.body(), {
                headers: { 'Content-Type': 'multipart/form-data; boundary=' + fd.boundary },
            });
            check(res, {
                'is status 200': (r) => r.status === 200,
            });
            break;
        default:
            console.log("The number is not between 1 and 4");
            break;
    }
}

function elastic_api (){
    http.get(`http://svheyapple.com/heyapple/_search?q=name:Apple`);
}

function fruit_api (){
    http.get(`http://svheyapple.com/api/v1/fruits/1`);
}
function email_api (){
    let email = "1106q@naver.com"
    http.get(`http://svheyapple.com/api/v1/bills?email=${email}&orderpayment_id=e9172f67-6a3e-4f41-8ed0-2ae3e80e1145`);
}
function upload_api (){
    const fd = new FormData();
    fd.append('images', http.file(img1, '5.jpg', 'image/jpeg'));
    const res = http.post('http://svheyapple.com/api/v1/orders/tasks', fd.body(), {
        headers: { 'Content-Type': 'multipart/form-data; boundary=' + fd.boundary },
    });
    check(res, {
        'is status 200': (r) => r.status === 200,
    });
}import http from 'k6/http';
import { sleep } from 'k6';
import { FormData } from 'https://jslib.k6.io/formdata/0.0.2/index.js';

export const options = {
    stages: [
        { duration: '30s' , target: 30},
        { duration: '20s' , target: 10},
        { duration: '10s' , target: 5},
    ],
};

//random api start
let randomNumber = Math.floor(Math.random() * 4) + 1;
const img1 = open('monitor/testImg/5.jpg', 'b');
export default function () {
    sleep(1);
    switch (randomNumber) {
        case 1:
            console.log("elastic test");
            elastic_api ();
            break;
        case 2:
            console.log("fruit test");
            fruit_api ();
            break;
        case 3:
            console.log("email test");
            email_api ();
            break;
        case 4:
            console.log("upload_api");
            const fd = new FormData();
            fd.append('images', http.file(img1, '5.jpg', 'image/jpeg'));
            const res = http.post('http://svheyapple.com/api/v1/orders/tasks', fd.body(), {
                headers: { 'Content-Type': 'multipart/form-data; boundary=' + fd.boundary },
            });
            check(res, {
                'is status 200': (r) => r.status === 200,
            });
            break;
        default:
            console.log("The number is not between 1 and 4");
            break;
    }
}

function elastic_api (){
    http.get(`http://svheyapple.com/heyapple/_search?q=name:Apple`);
}

function fruit_api (){
    http.get(`http://svheyapple.com/api/v1/fruits/1`);
}
function email_api (){
    let email = "1106q@naver.com"
    http.get(`http://svheyapple.com/api/v1/bills?email=${email}&orderpayment_id=e9172f67-6a3e-4f41-8ed0-2ae3e80e1145`);
}
function upload_api (){
    const fd = new FormData();
    fd.append('images', http.file(img1, '5.jpg', 'image/jpeg'));
    const res = http.post('http://svheyapple.com/api/v1/orders/tasks', fd.body(), {
        headers: { 'Content-Type': 'multipart/form-data; boundary=' + fd.boundary },
    });
    check(res, {
        'is status 200': (r) => r.status === 200,
    });
}