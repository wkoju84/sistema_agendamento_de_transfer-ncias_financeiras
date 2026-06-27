<template>
  <div class="card">
    <div class="card-header">
      <h2 class="card-title">Extrato de agendamentos</h2>
      <span class="count-badge">{{ transferencias.length }} registro{{ transferencias.length !== 1 ? 's' : '' }}</span>
    </div>

    <div v-if="carregando" class="empty-state">
      Carregando...
    </div>

    <div v-else-if="erro" class="error-state">
      {{ erro }}
    </div>

    <div v-else-if="transferencias.length === 0" class="empty-state">
      Nenhum agendamento cadastrado
    </div>

    <div v-else class="table-wrapper">
      <table>
        <thead>
          <tr>
            <th>Conta origem</th>
            <th>Conta destino</th>
            <th>Valor</th>
            <th>Taxa</th>
            <th>Data transferência</th>
            <th>Data agendamento</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="t in transferencias" :key="t.id">
            <td>{{ t.contaOrigem }}</td>
            <td>{{ t.contaDestino }}</td>
            <td>{{ formatarValor(t.valor) }}</td>
            <td>{{ formatarValor(t.taxa) }}</td>
            <td>{{ formatarData(t.dataTransferencia) }}</td>
            <td>{{ formatarData(t.dataAgendamento) }}</td>
            <td><span class="badge">Agendado</span></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import transferenciaService from '../services/transferenciaService'

export default {
  name: 'ExtratoView',
  data() {
    return {
      transferencias: [],
      carregando: false,
      erro: null
    }
  },
  mounted() {
    this.carregar()
  },
  methods: {
    async carregar() {
      this.carregando = true
      this.erro = null
      try {
        const resposta = await transferenciaService.listarTodos()
        this.transferencias = resposta.data
      } catch (e) {
        this.erro = 'Erro ao carregar os agendamentos. Verifique se o back-end está rodando.'
      } finally {
        this.carregando = false
      }
    },
    formatarValor(valor) {
      return 'R$ ' + parseFloat(valor).toFixed(2).replace('.', ',')
    },
    formatarData(data) {
      if (!data) return '—'
      const [ano, mes, dia] = data.split('-')
      return `${dia}/${mes}/${ano}`
    }
  }
}
</script>

<style scoped>
.card {
  background: #ffffff;
  border-radius: 12px;
  border: 1px solid #e0e0e0;
  padding: 1.5rem;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.25rem;
}

.card-title {
  font-size: 16px;
  font-weight: 600;
  color: #333;
}

.count-badge {
  background: #e8f0fe;
  color: #1a73e8;
  font-size: 12px;
  font-weight: 500;
  padding: 4px 12px;
  border-radius: 99px;
}

.empty-state {
  text-align: center;
  padding: 2.5rem;
  color: #999;
  font-size: 14px;
}

.error-state {
  text-align: center;
  padding: 2.5rem;
  color: #c62828;
  font-size: 14px;
  background: #fdecea;
  border-radius: 6px;
}

.table-wrapper {
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
  font-size: 13px;
}

th {
  text-align: left;
  padding: 8px 12px;
  color: #888;
  font-weight: 500;
  border-bottom: 1px solid #e0e0e0;
  font-size: 12px;
  white-space: nowrap;
}

td {
  padding: 10px 12px;
  border-bottom: 1px solid #f0f0f0;
  color: #333;
  white-space: nowrap;
}

tr:last-child td {
  border-bottom: none;
}

tr:hover td {
  background: #f9f9f9;
}

.badge {
  display: inline-block;
  padding: 3px 10px;
  border-radius: 99px;
  font-size: 11px;
  font-weight: 500;
  background: #e8f5e9;
  color: #2e7d32;
}
</style>