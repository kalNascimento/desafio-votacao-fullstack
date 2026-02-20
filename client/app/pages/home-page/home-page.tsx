import { Button } from "~/components/ui/button";
import { SectionForm } from "~/components/layout/section-form";
import { Table } from "~/components/layout/table";
import { useEffect, useState } from "react";
import { criarPauta } from "~/services/pauta.service";
import { FormCriarPauta } from '~/components/layout/form-criar-pauta';
import { FormCriarSessao } from '~/components/layout/form-criar-sessao';
import { criarSessao, listarSessao } from '~/services/sessao.service';
import { TableColumn } from '~/types/table-column';
import { SessaoVotacaoResponse } from '~/types/sessao-votacao.response';
import { CriarSessaoVotacaoRequest } from '~/types/criar-sessao-votacao.request';
import { CriarPautaRequest } from '~/types/criar-pauta.request';

export function HomePage() {
  const [openPauta, setOpenPauta] = useState(false);
  const [openSessao, setOpenSessao] = useState(false);
  const [sessoes, setSessoes] = useState<SessaoVotacaoResponse[]>([])

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
      className: "col-span-2 text-left",
      render: (row) => row.pauta.nome,
    },
    {
      key: "pauta.descricao",
      header: "Descrição",
      className: "col-span-4 text-left",
      render: (row) => row.pauta.descricao,
    },
    {
      key: "acoes",
      header: "Ações",
      className: "col-span-2 justify-center",
      render: () => <Button type="button">Votar</Button>,
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
    </main>
  );
}
