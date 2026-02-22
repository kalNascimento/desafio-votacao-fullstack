import { SessaoStatusEnum } from '~/enums/sessao-status.enum';
import { PautaResponse } from './pauta.response'

export interface SessaoVotacaoResponse {
  pauta: PautaResponse;
  status: SessaoStatusEnum;
  dataHoraFinalizacao: string;
  votoVencedor: string;
  id: string;
}