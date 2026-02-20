import { api } from "./api";

export function criarPauta(payload: any) {
  return api.post("/pauta", payload);
}
