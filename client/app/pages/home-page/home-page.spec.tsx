import { render, screen, waitFor, fireEvent } from "@testing-library/react"
import "@testing-library/jest-dom"
import { HomePage } from "./home-page"

jest.mock("~/services/sessao.service", () => ({
  listarSessao: jest.fn(),
  obterSessao: jest.fn(),
  finalizarSessao: jest.fn(),
  criarSessao: jest.fn(),
}))

jest.mock("~/services/pauta.service", () => ({
  criarPauta: jest.fn(),
  listarPauta: jest.fn(),
}))

jest.mock("~/services/voto.service", () => ({
  votar: jest.fn(),
}))

import {
  listarSessao,
  obterSessao,
  finalizarSessao,
} from "~/services/sessao.service"
import { listarPauta } from '~/services/pauta.service'

describe("HomePage - integração completa", () => {

  const mockSessao = {
    id: "1",
    status: "ABERTA",
    dataHoraFinalizacao: new Date().toISOString(),
    votoVencedor: "SIM",
    pauta: {
      id: "10",
      nome: "Pauta Teste",
      descricao: "Descrição teste",
    },
  }

  beforeEach(() => {
    jest.clearAllMocks();

    (listarSessao as jest.Mock).mockResolvedValue({
      data: { content: [mockSessao] },
    });

    (obterSessao as jest.Mock).mockResolvedValue({
      data: mockSessao,
    });

    (finalizarSessao as jest.Mock).mockResolvedValue({});

    (listarPauta as jest.Mock).mockResolvedValue({});
  });

  test("deve renderizar títulos principais", async () => {
    render(<HomePage />)

    await screen.findByText("Lista de sessões abertas")

    expect(screen.getByText("Pauta")).toBeInTheDocument()
    expect(screen.getByText("Sessao")).toBeInTheDocument()
  })

  test("deve chamar listarSessao ao montar", async () => {
    render(<HomePage />)

    await waitFor(() => {
      expect(listarSessao).toHaveBeenCalledTimes(1)
    })
  })

  test("deve renderizar dados da tabela", async () => {
    render(<HomePage />)

    expect(await screen.findByText("Pauta Teste")).toBeInTheDocument()
    expect(screen.getByText("Descrição teste")).toBeInTheDocument()
  })

  test("deve abrir modal de detalhes", async () => {
    render(<HomePage />)

    await screen.findByText("Pauta Teste")

    const detalhesButton = screen.getByRole("button", { name: /Detalhes/i })

    fireEvent.click(detalhesButton)

    await waitFor(() => {
      expect(obterSessao).toHaveBeenCalledWith("1")
    })
  })

  test("deve abrir modal de votar", async () => {
    render(<HomePage />)

    await screen.findByText("Pauta Teste")

    const votarButton = screen.getByRole("button", { name: /Votar/i })

    fireEvent.click(votarButton)

    expect(screen.getByText(/Cancelar/i)).toBeInTheDocument()
  })

  test("deve finalizar sessão", async () => {
    render(<HomePage />)

    await screen.findByText("Pauta Teste")

    const finalizarButton = screen.getByRole("button", { name: /Finalizar/i })

    fireEvent.click(finalizarButton)

    await waitFor(() => {
      expect(finalizarSessao).toHaveBeenCalledWith("1")
    })
  })

})