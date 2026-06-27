<template>
  <div class="card">
    <h2 class="card-title">Nova transferência</h2>

    <div class="form-grid">
      <div class="form-group">
        <label>Conta de origem</label>
        <input
          v-model="form.contaOrigem"
          type="text"
          maxlength="10"
          placeholder="0000000000"
          @input="calcularTaxa"
        />
        <span class="error" v-if="erros.contaOrigem">{{ erros.contaOrigem }}</span>
      </div>

      <div class="form-group">
        <label>Conta de destino</label>
        <input
          v-model="form.contaDestino"
          type="text"
          maxlength="10"
          placeholder="0000000000"
          @input="calcularTaxa"
        />
        <span class="error" v-if="erros.contaDestino">{{ erros.contaDestino }}</span>
      </div>

      <div class="form-group">
        <label>Valor (R$)</label>
        <input
          v-model="form.valor"
          type="number"
          min="0.01"
          step="0.01"
          placeholder="0,00"
          @input="calcularTaxa"
        />
        <span class="error" v-if="erros.valor">{{ erros.valor }}</span>
      </div>

      <div class="form-group">
        <label>Data da transferência</label>
        <input
          v-model="form.dataTransferencia"
          type="date"
          @input="calcularTaxa"
        />
        <span class="error" v-if="erros.dataTransferencia">{{ erros.dataTransferencia }}</span>
      </div>

      <div class="form-group full">
        <label>Taxa calculada</label>
        <div class="taxa-box" :class="{ 'taxa-erro': taxaInvalida }">
          <span class="taxa-label">{{ taxaDescricao }}</span>
          <span class="taxa-valor">{{ taxaFormatada }}</span>
        </div>
      </div>
    </div>

    <div class="error taxa-error-msg" v-if="erros.api">{{ erros.api }}</div>
    <div class="success-msg" v-if="sucesso">Transferência agendada com sucesso!</div>

    <button class="btn-primary" @click="agendar">Agendar transferência</button>
  </div>
</template>

<script>
import transferenciaService from '../services/transferenciaService'

export default {
  name: 'Agendar',
  data() {
    return {
      form: {
        contaOrigem: '',
        contaDestino: '',
        valor: '',
        dataTransferencia: ''
      },
      taxa: null,
      taxaDescricao: 'Preencha os campos acima',
      taxaInvalida: false,
      erros: {},
      sucesso: false
    }
  },
  computed: {
    taxaFormatada() {
      if (this.taxa === null) return 'R$ —'
      return 'R$ ' + this.taxa.toFixed(2).replace('.', ',')
    }
  },
  methods: {
    calcularTaxa() {
      this.taxa = null
      this.taxaInvalida = false
      this.taxaDescricao = 'Preencha os campos acima'

      const valor = parseFloat(this.form.valor)
      const data = this.form.dataTransferencia
      if (!valor || !data) return

      const hoje = new Date()
      hoje.setHours(0, 0, 0, 0)
      const dataTransferencia = new Date(data + 'T00:00:00')
      const dias = Math.round((dataTransferencia - hoje) / 86400000)

      if (dias === 0) {
        this.taxa = 3.00 + valor * 0.025
        this.taxaDescricao = 'Dia 0: R$ 3,00 + 2,5%'
      } else if (dias >= 1 && dias <= 10) {
        this.taxa = 12.00
        this.taxaDescricao = '1 a 10 dias: R$ 12,00'
      } else if (dias >= 11 && dias <= 20) {
        this.taxa = valor * 0.082
        this.taxaDescricao = '11 a 20 dias: 8,2%'
      } else if (dias >= 21 && dias <= 30) {
        this.taxa = valor * 0.069
        this.taxaDescricao = '21 a 30 dias: 6,9%'
      } else if (dias >= 31 && dias <= 40) {
        this.taxa = valor * 0.047
        this.taxaDescricao = '31 a 40 dias: 4,7%'
      } else if (dias >= 41 && dias <= 50) {
        this.taxa = valor * 0.017
        this.taxaDescricao = '41 a 50 dias: 1,7%'
      } else {
        this.taxaInvalida = true
        this.taxaDescricao = 'Período inválido — máximo 50 dias'
      }
    },

    validar() {
      this.erros = {}
      const re = /^\d{10}$/

      if (!re.test(this.form.contaOrigem))
        this.erros.contaOrigem = 'Deve conter 10 dígitos numéricos'

      if (!re.test(this.form.contaDestino))
        this.erros.contaDestino = 'Deve conter 10 dígitos numéricos'

      if (!this.form.valor || parseFloat(this.form.valor) <= 0)
        this.erros.valor = 'Informe um valor maior que zero'

      if (!this.form.dataTransferencia)
        this.erros.dataTransferencia = 'Informe a data da transferência'

      if (this.taxaInvalida)
        this.erros.api = 'Não há taxa aplicável para o período informado (máximo 50 dias)'

      return Object.keys(this.erros).length === 0
    },

    async agendar() {
      this.sucesso = false
      this.erros = {}

      if (!this.validar()) return

      try {
        await transferenciaService.agendar({
          contaOrigem: this.form.contaOrigem,
          contaDestino: this.form.contaDestino,
          valor: parseFloat(this.form.valor),
          dataTransferencia: this.form.dataTransferencia
        })

        this.sucesso = true
        this.form = { contaOrigem: '', contaDestino: '', valor: '', dataTransferencia: '' }
        this.taxa = null
        this.taxaDescricao = 'Preencha os campos acima'

      } catch (erro) {
        if (erro.response?.status === 422) {
          this.erros.api = erro.response.data.erro
        } else if (erro.response?.status === 400) {
          this.erros = { ...this.erros, ...erro.response.data }
        } else {
          this.erros.api = 'Erro ao conectar com o servidor. Verifique se o back-end está rodando.'
        }
      }
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

.card-title {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 1.5rem;
  color: #333;
}

.form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.form-group.full {
  grid-column: 1 / -1;
}

label {
  font-size: 13px;
  font-weight: 500;
  color: #555;
}

input {
  padding: 8px 12px;
  border: 1px solid #d0d0d0;
  border-radius: 6px;
  font-size: 14px;
  color: #333;
  outline: none;
  transition: border 0.15s;
}

input:focus {
  border-color: #1a73e8;
}

.taxa-box {
  background: #e8f0fe;
  border: 1px solid #c5d8fc;
  border-radius: 6px;
  padding: 10px 14px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.taxa-box.taxa-erro {
  background: #fdecea;
  border-color: #f5c6c6;
}

.taxa-label {
  font-size: 13px;
  color: #1a73e8;
}

.taxa-box.taxa-erro .taxa-label {
  color: #c62828;
}

.taxa-valor {
  font-size: 15px;
  font-weight: 600;
  color: #1a73e8;
}

.taxa-box.taxa-erro .taxa-valor {
  color: #c62828;
}

.error {
  font-size: 12px;
  color: #c62828;
}

.taxa-error-msg {
  margin-top: 0.75rem;
}

.success-msg {
  margin-top: 0.75rem;
  font-size: 13px;
  color: #2e7d32;
  background: #e8f5e9;
  border: 1px solid #c8e6c9;
  border-radius: 6px;
  padding: 10px 14px;
}

.btn-primary {
  margin-top: 1.25rem;
  width: 100%;
  padding: 10px;
  background: #1a73e8;
  color: #ffffff;
  border: none;
  border-radius: 6px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: background 0.15s;
}

.btn-primary:hover {
  background: #1557b0;
}
</style>