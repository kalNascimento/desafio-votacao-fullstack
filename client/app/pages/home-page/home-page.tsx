import { Button } from "~/components/ui/button";
import { SectionForm } from "~/components/layout/section-form";
import { Table } from "~/components/layout/table";
import { useEffect, useState } from "react";
import { criarPauta } from "~/services/pauta.service";
import { FormCriarPauta } from '~/components/layout/form-criar-pauta';
import { FormCriarSessao } from '~/components/layout/form-criar-sessao';
import { criarSessao, finalizarSessao, listarSessao, obterSessao } from '~/services/sessao.service';
import { TableColumn } from '~/types/table-column';
import { SessaoVotacaoResponse } from '~/types/sessao-votacao.response';
import { CriarSessaoVotacaoRequest } from '~/types/criar-sessao-votacao.request';
import { CriarPautaRequest } from '~/types/criar-pauta.request';
import { Modal } from '~/components/ui/modal';
import { getStatusLabel } from '~/enums/sessao-status.enum';
import { VotoRequest } from '~/types/voto.request';
import { votar } from '~/services/voto.service';
import { FormVotar } from '~/components/layout/form-votar';

export function HomePage() {
  const [openPauta, setOpenPauta] = useState(false);
  const [openSessao, setOpenSessao] = useState(false);
  const [openVisualizar, setOpenVisualizar] = useState(false);
  const [openVotar, setOpenVotar] = useState(false);
  const [sessoes, setSessoes] = useState<SessaoVotacaoResponse[]>([])
  const [sessaoDetalhe, setSessaoDetalhes] = useState<SessaoVotacaoResponse>();
  const [idSessaoVoto, setIdSessaoVoto] = useState<string>();

  useEffect(() => {
    listarSessao({ page: 0, size: 10 }).then(res => setSessoes(res.data.content))
  }, [])

  async function handleCreatePauta(data: CriarPautaRequest) {
    await criarPauta(data);

    setOpenPauta(false);
  }

  async function handleCreateSessao(data: CriarSessaoVotacaoRequest) {
    await criarSessao(data);
    listarSessao({ page: 0, size: 10 }).then(res => setSessoes(res.data.content))
    setOpenSessao(false);
  }

  async function handleFinalizar(id: string) {
    await finalizarSessao(id);
    listarSessao({ page: 0, size: 10 }).then(res => setSessoes(res.data.content))
  }

  async function handleSessaoDetalhes(id: string) {
    await obterSessao(id).then(res => setSessaoDetalhes(res.data));

    setOpenVisualizar(true);
  }

  async function handleVotas(payload: VotoRequest) {
    await votar(payload);
  }

  const columns: TableColumn<SessaoVotacaoResponse>[] = [
    {
      key: "pauta.id",
      header: "ID",
      className: "col-span-4 text-left",
      render: (row) => row.pauta.id,
    },
    {
      key: "pauta.nome",
      header: "Nome",
      className: "col-span-1 text-left",
      render: (row) => row.pauta.nome,
    },
    {
      key: "pauta.descricao",
      header: "Descrição",
      className: "col-span-3 text-left",
      render: (row) => row.pauta.descricao,
    },
    {
      key: "visualizar",
      header: "Visualizar",
      className: "col-span-2 justify-center",
      render: (row) => <div>
        <Button type="button" variant="secondary" onClick={() => handleSessaoDetalhes(row.id)}>
          Detalhes
        </Button>
        {sessaoDetalhe?.id === row.id && (
          <Modal isVisible={openVisualizar} onClose={() => setOpenVisualizar(false)}>
            <div>
              {getStatusLabel(sessaoDetalhe.status)} - {
                new Date(sessaoDetalhe.dataHoraFinalizacao).toLocaleString("pt-BR", {
                  day: "2-digit",
                  month: "2-digit",
                  year: "numeric",
                  hour: "2-digit",
                  minute: "2-digit",
                  hour12: false,
                })
              } - Vencedor: {sessaoDetalhe.votoVencedor}
            </div>
          </Modal>
        )}
      </div>
    },
    {
      key: "acoes",
      header: "Ações",
      className: "col-span-2 justify-center",
      render: (row) => <div className='flex gap-2'>
        <Button type="button" onClick={() => {
          setOpenVotar(true);
          setIdSessaoVoto(row.id);
        }}>Votar</Button>
        <Button type="button" onClick={() => handleFinalizar(row.id)} variant="danger">
          Finalizar
        </Button>
      </div>
    },
  ];

  return (
    <main className="width-barrier flex flex-col items-center gap-8 p-8 border-2 border-primary-1 rounded-md mx-auto">
      <SectionForm
        titulo="Pauta"
        buttonConteudo="Criar pauta"
        visible={openPauta}
        onVisibleChange={setOpenPauta}
        modalConteudo={
          <FormCriarPauta
            onSubmit={handleCreatePauta}
            onCancel={() => setOpenPauta(false)}
          />
        }
      />

      <hr className="w-full border-2 border-primary-3 my-4" />

      <SectionForm
        titulo="Sessao"
        buttonConteudo="Abrir uma sessão"
        visible={openSessao}
        onVisibleChange={setOpenSessao}
        modalConteudo={<FormCriarSessao
          onSubmit={handleCreateSessao}
          onCancel={() => setOpenSessao(false)}
        />
        }
      />

      <hr className="w-full border-2 border-primary-3 my-4" />

      <h2 className="text-5xl font-bold text-base-2">
        Lista de sessões abertas
      </h2>

      <Table columns={columns} data={sessoes} />

      {
        idSessaoVoto
        && <Modal
          children={<FormVotar
            onSubmit={handleVotas}
            onCancel={() => setOpenVotar(false)}
            idSessao={idSessaoVoto} />}
          isVisible={openVotar}
          onClose={() => setOpenVotar(false)} />
      }
    </main>
  );
}
