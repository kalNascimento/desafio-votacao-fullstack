import { VotoEnum } from '~/enums/voto.enum';
import { AssociadoRequest } from './associado.request';

export interface VotoRequest {
  idSessao: string;
  voto: VotoEnum;
  associado: AssociadoRequest;
}