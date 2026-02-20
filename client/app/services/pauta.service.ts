import { CriarPautaRequest } from '~/types/criar-pauta.request';
import { api } from "./api";
import { AxiosResponse } from 'axios';
import { PautaComboResponse } from '~/types/pauta-combo.response';

export function criarPauta(payload: CriarPautaRequest): Promise<void> {
  return api.post("/pauta", payload);
}

export function listarPauta(): Promise<AxiosResponse<PautaComboResponse[]>> {
  return api.get<PautaComboResponse[]>("/pauta");
}