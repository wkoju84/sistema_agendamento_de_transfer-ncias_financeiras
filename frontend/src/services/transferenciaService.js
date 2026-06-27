import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080'
})

export default {
  agendar(dados) {
    return api.post('/transferencias', dados)
  },

  listarTodos() {
    return api.get('/transferencias')
  }
}