import { render, screen, fireEvent, waitFor } from "@testing-library/react"
import "@testing-library/jest-dom"
import { FormCriarSessao } from "./form-criar-sessao"
import { listarPauta } from "~/services/pauta.service"

jest.mock("~/services/pauta.service", () => ({
  listarPauta: jest.fn(),
}))

const mockPautas = [
  { id: "1", nome: "Pauta 1" },
  { id: "2", nome: "Pauta 2" },
]

describe("FormCriarSessao", () => {

  beforeEach(() => {
    jest.clearAllMocks();

    (listarPauta as jest.Mock).mockResolvedValue({
      data: mockPautas,
    })
  })

  it("deve renderizar campos do formulário", async () => {
    render(<FormCriarSessao onSubmit={jest.fn()} />)

    expect(screen.getByText("Criar Sessão")).toBeInTheDocument()

    await waitFor(() => {
      expect(screen.getByText("Pauta 1")).toBeInTheDocument()
    })

    expect(screen.getByText("Criar")).toBeInTheDocument()
    expect(screen.getByText("Cancelar")).toBeInTheDocument()
  })

  it("deve chamar listarPauta ao montar", async () => {
    render(<FormCriarSessao onSubmit={jest.fn()} />)

    await waitFor(() => {
      expect(listarPauta).toHaveBeenCalled()
    })
  })

  it("deve preencher campos e enviar formulário", async () => {
    const onSubmit = jest.fn<Promise<void>, [any]>().mockResolvedValue(undefined)

    render(<FormCriarSessao onSubmit={onSubmit} />)

    await screen.findByText("Pauta 1")

    fireEvent.change(screen.getByRole("combobox"), {
      target: { value: "1" },
    })

    fireEvent.change(screen.getByDisplayValue(""), {
      target: { value: "2026-02-23T10:00" },
    })

    fireEvent.click(screen.getByText("Criar"))

    await waitFor(() => {
      expect(onSubmit).toHaveBeenCalledWith({
        idPauta: "1",
        dataHoraFinalizacao: "2026-02-23T10:00",
      })
    })
  })

  it("deve mostrar loading ao enviar", async () => {
    const onSubmit = jest.fn(
      () => new Promise<void>((resolve) => setTimeout(resolve, 100))
    )

    render(<FormCriarSessao onSubmit={onSubmit} />)

    await waitFor(() => {
      expect(screen.getByText("Pauta 1")).toBeInTheDocument()
    })

    fireEvent.change(screen.getByRole("combobox"), {
      target: { value: "1" },
    })

    const inputDate = screen.getByDisplayValue("")
    fireEvent.change(inputDate, {
      target: { value: "2026-01-01T10:00" },
    })

    fireEvent.click(screen.getByText("Criar"))

    expect(await screen.findByText("Enviando...")).toBeInTheDocument()
  })

  it("deve chamar onCancel ao clicar cancelar", async () => {
    const onCancel = jest.fn()

    render(<FormCriarSessao onSubmit={jest.fn()} onCancel={onCancel} />)

    await waitFor(() => {
      expect(screen.getByText("Pauta 1")).toBeInTheDocument()
    })

    fireEvent.click(screen.getByText("Cancelar"))

    expect(onCancel).toHaveBeenCalled()
  })
})