import axios from 'axios'
import config from './config'

const { baseURL } = config
const axiosConfig = {
  baseURL,
  headers: {
    'Content-Type': 'application/json; charset=utf-8',
    'Access-Control-Allow-Origin': '*',
  },
}
const apiCall = axios.create(axiosConfig)
export default apiCall
