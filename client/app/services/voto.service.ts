import { VotoRequest } from '~/types/voto.request';
import { api } from './api';

export function votar(payload: VotoRequest): Promise<void> {
  return api.post("/voto", payload);
}