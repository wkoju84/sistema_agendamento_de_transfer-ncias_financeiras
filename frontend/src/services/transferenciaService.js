import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080'
})

api.interceptors.response.use(
  response => response,
  error => {
    if (!error.response) {
      return Promise.reject({
        response: {
          status: 503,
          data: { erro: 'Não foi possível conectar ao servidor. Verifique se o back-end está rodando.' }
        }
      })
    }
    return Promise.reject(error)
  }
)

export default {
  agendar(dados) {
    return api.post('/transferencias', dados)
  },

  listarTodos() {
    return api.get('/transferencias')
  }
}