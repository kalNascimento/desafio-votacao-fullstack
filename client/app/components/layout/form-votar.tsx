import { useEffect, useState } from 'react';
import { Select } from '../ui/select';
import { listarPauta } from '~/services/pauta.service';
import { PautaComboResponse } from '~/types/pauta-combo.response';
import { DateTimePicker } from '../ui/date-picker-time';
import { Button } from '../ui/button';
import { CriarSessaoVotacaoRequest } from '~/types/criar-sessao-votacao.request';
import { VotoRequest } from '~/types/voto.request';
import { VotoEnum } from '~/enums/voto.enum';

export interface FormDataCriarSessao {
  idPauta: string;
  dataHora: string;
}

interface FormVotarProps {
  onSubmit: (data: VotoRequest) => Promise<void> | void;
  onCancel?: () => void;
  idSessao: string;
}

export function FormVotar({ onSubmit, onCancel, idSessao }: FormVotarProps) {
  const [pautas, setPautas] = useState<PautaComboResponse[]>([]);
  const [form, setForm] = useState<VotoRequest>({
    idSessao: idSessao,
    voto: VotoEnum.DECLINAR,
    associado: {
      nome: '',
      cpf: ''
    }
  });

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    listarPauta().then((res) => setPautas(res.data));
  }, []);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleAssociadoChange = (
    e: React.ChangeEvent<HTMLInputElement>
  ) => {
    const { name, value } = e.target;

    setForm((prev) => ({
      ...prev,
      associado: {
        ...prev.associado,
        [name]: value,
      },
    }));
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

      <div className="flex flex-col">
        <label className="text-sm font-medium mb-1">Nome</label>
        <input
          type="text"
          name="nome"
          value={form.associado.nome}
          onChange={handleAssociadoChange}
          required
          className="border rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-primary-500"
          placeholder="Digite o nome"
        />
      </div>

      <div className="flex flex-col">
        <label className="text-sm font-medium mb-1">CPF</label>
        <input
          type="text"
          name="cpf"
          value={form.associado.cpf}
          onChange={handleAssociadoChange}
          required
          className="border rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-primary-500"
          placeholder="Digite o cpf"
        />
      </div>

      <Select
        label="Voto"
        name="voto"
        value={form.voto}
        onChange={handleChange}
        required
        placeholder="Selecione o voto"
        options={[
          {
            label: VotoEnum.ACEITAR,
            value: VotoEnum.ACEITAR,
          },
          {
            label: VotoEnum.DECLINAR,
            value: VotoEnum.DECLINAR,
          }
        ]}
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