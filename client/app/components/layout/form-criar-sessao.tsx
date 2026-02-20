import { useEffect, useState } from 'react';
import { Select } from '../ui/select';
import { listarPauta } from '~/services/pauta.service';
import { PautaComboResponse } from '~/types/pauta-combo.response';
import { DateTimePicker } from '../ui/date-picker-time';
import { Button } from '../ui/button';
import { CriarSessaoVotacaoRequest } from '~/types/criar-sessao-votacao.request';

export interface FormDataCriarSessao {
  idPauta: string;
  dataHora: string;
}

interface FormCriarSessaoProps {
  onSubmit: (data: CriarSessaoVotacaoRequest) => Promise<void> | void;
  onCancel?: () => void;
}

export function FormCriarSessao({ onSubmit, onCancel }: FormCriarSessaoProps) {
  const [pautas, setPautas] = useState<PautaComboResponse[]>([]);
  const [form, setForm] = useState<CriarSessaoVotacaoRequest>({
    idPauta: '',
    dataHoraFinalizacao: '',
  });

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    listarPauta().then((res) => setPautas(res.data));
  }, []);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    try {
      await onSubmit(form);
    } catch (error) {
      alert('Erro ao enviar');
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="flex flex-col gap-4">
      <h2 className="text-xl font-semibold">Criar Sessão</h2>

      <Select
        label="Pauta"
        name="idPauta"
        value={form.idPauta}
        onChange={handleChange}
        required
        placeholder="Selecione uma pauta"
        options={pautas.map((pauta) => ({
          label: pauta.nome,
          value: String(pauta.id),
        }))}
      />

      <DateTimePicker
        label="Data e Hora da Sessão"
        name="dataHoraFinalizacao"
        value={form.dataHoraFinalizacao}
        onChange={handleChange}
        required
      />

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