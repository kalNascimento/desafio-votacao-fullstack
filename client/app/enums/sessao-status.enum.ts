export enum SessaoStatusEnum {
  EM_ANDAMENTO = "EM_ANDAMENTO",
  FINALIZADA = "FINALIZADA"
}

const SESSAO_STATUS_LABEL: Record<SessaoStatusEnum, string> = {
  [SessaoStatusEnum.EM_ANDAMENTO]: "Em andamento",
  [SessaoStatusEnum.FINALIZADA]: "Finalizada",
};

export function getStatusLabel(enumValue: SessaoStatusEnum) {
  return SESSAO_STATUS_LABEL[enumValue];
}