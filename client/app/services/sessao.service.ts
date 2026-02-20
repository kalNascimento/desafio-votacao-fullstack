import { AxiosResponse } from 'axios';
import { api } from './api';
import { SessaoVotacaoResponse } from '~/types/sessao-votacao.response';
import { PageResponse } from '~/types/page.response';
import { PageRequest } from '~/types/page.request';
import { CriarSessaoVotacaoRequest } from '~/types/criar-sessao-votacao.request';

export function criarSessao(payload: CriarSessaoVotacaoRequest): Promise<void> {
  return api.post("/sessao-votacao", payload);
}

export function listarSessao(payload: PageRequest): Promise<AxiosResponse<PageResponse<SessaoVotacaoResponse>>> {
  return api.get<PageResponse<SessaoVotacaoResponse>>("/sessao-votacao", { params: payload });
}