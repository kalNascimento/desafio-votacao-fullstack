import { PautaResponse } from './pauta.response'

export interface SessaoVotacaoResponse {
  pauta: PautaResponse
  status: string
  dataHoraFinalizacao: string
}