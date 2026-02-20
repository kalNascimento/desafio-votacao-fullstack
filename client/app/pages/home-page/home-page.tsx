import { Button } from "~/components/ui/button";
import { SectionForm } from "~/components/layout/section-form";
import { Table } from "~/components/layout/table";
import { useState } from "react";
import { criarPauta } from "~/services/pauta.service";
import { FormCriarPauta } from '~/components/layout/form-criar-pauta';

export function HomePage() {
  const [open, setOpen] = useState(false);

  const data = [
    { id: 1, nome: "Sessão 1", descricao: "Descrição 1" },
    { id: 2, nome: "Sessão 2", descricao: "Descrição 2" },
  ];

  async function handleCreate(data: { nome: string; descricao: string }) {
    await criarPauta(data);

    setOpen(false);
  }

  const columns = [
    {
      key: "id",
      header: "ID",
      className: "col-span-2 text-left",
    },
    {
      key: "nome",
      header: "Nome",
      className: "col-span-3 text-left",
    },
    {
      key: "descricao",
      header: "Descrição",
      className: "col-span-5 text-left",
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
        visible={open}
        onVisibleChange={setOpen}
        modalConteudo={
          <FormCriarPauta
            onSubmit={handleCreate}
            onCancel={() => setOpen(false)}
          />
        }
      />

      <hr className="w-full border-2 border-primary-3 my-4" />

      <SectionForm titulo="Sessao" buttonConteudo="Abrir uma sessão" />

      <hr className="w-full border-2 border-primary-3 my-4" />

      <h2 className="text-5xl font-bold text-base-2">
        Lista de sessões abertas
      </h2>

      <Table columns={columns} data={data} />
    </main>
  );
}
