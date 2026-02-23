import { useState } from 'react';
import { Button } from '../ui/button';

export interface FormDataCriarPauta {
  nome: string;
  descricao: string;
};

interface FormCriarPautaProps {
  onSubmit: (data: FormDataCriarPauta) => Promise<void> | void;
  onCancel?: () => void;
};

export function FormCriarPauta({
  onSubmit,
  onCancel,
}: FormCriarPautaProps) {
  const [form, setForm] = useState<FormDataCriarPauta>({
    nome: '',
    descricao: '',
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
      await onSubmit(form);
    } catch (error) {
      alert('Erro ao enviar');
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

      <Button type="submit" disabled={loading}>
        {loading ? 'Enviando...' : 'Criar'}
      </Button>

      <Button
        type="button"
        variant="secondary"
        onClick={onCancel}
        disabled={loading}
      >
        Cancelar
      </Button>
    </form>
  );
}