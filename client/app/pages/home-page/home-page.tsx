import { Button } from "~/components/ui/button";
import { SectionForm } from "~/components/layout/section-form";
import { Table } from "~/components/layout/table";
import { useState } from "react";
import { criarPauta } from "~/services/pauta.service";

const criarPautaForm = () => {
  type FormData = {
    nome: string;
    descricao: string;
  };

  const [form, setForm] = useState<FormData>({
    nome: "",
    descricao: "",
  });

  const [loading, setLoading] = useState(false);

  function handleChange(
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ) {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setLoading(true);

    try {
      await criarPauta(form);
    } catch (error) {
      alert("Erro ao enviar");
    } finally {
      setLoading(false);
    }
  }

  return (
    <form onSubmit={handleSubmit} className="flex flex-col gap-4">
      <h2 className="text-xl font-semibold">Criar Pauta</h2>

      <div className="flex flex-col">
        <label className="text-sm font-medium mb-1">Nome</label>
        <input
          type="text"
          name="nome"
          value={form.nome}
          onChange={handleChange}
          required
          className="border rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-primary-500"
          placeholder="Digite o nome"
        />
      </div>

      <div className="flex flex-col">
        <label className="text-sm font-medium mb-1">Descrição</label>
        <textarea
          name="descricao"
          value={form.descricao}
          onChange={handleChange}
          required
          rows={4}
          className="border rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-primary-500"
          placeholder="Digite a descrição"
        />
      </div>

      <Button type="submit">Criar</Button>
    </form>
  );
};

export function HomePage() {
  const data = [
    { id: 1, nome: "Sessão 1", descricao: "Descrição 1" },
    { id: 2, nome: "Sessão 2", descricao: "Descrição 2" },
  ];

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
      <SectionForm titulo="Pauta" buttonConteudo="Criar pauta" modalConteudo={criarPautaForm()} />

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
