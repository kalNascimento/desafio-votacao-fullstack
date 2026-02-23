import { render, screen, fireEvent, waitFor } from "@testing-library/react"
import "@testing-library/jest-dom"
import { FormVotar } from "./form-votar"
import { listarPauta } from "~/services/pauta.service"
import { VotoEnum } from "~/enums/voto.enum"

jest.mock("~/services/pauta.service", () => ({
  listarPauta: jest.fn(),
}))

describe("FormVotar", () => {

  beforeEach(() => {
    jest.clearAllMocks()

    ;(listarPauta as jest.Mock).mockResolvedValue({ data: [] })
  })

  const renderComponent = (props?: any) => {
    const defaultProps = {
      idSessao: "sessao-1",
      onSubmit: jest.fn().mockResolvedValue(undefined),
      onCancel: jest.fn(),
    }

    return render(<FormVotar {...defaultProps} {...props} />)
  }

  it("deve renderizar campos", async () => {
    renderComponent()

    await waitFor(() => expect(listarPauta).toHaveBeenCalled())

    expect(screen.getByText("Criar Sessão")).toBeInTheDocument()
    expect(screen.getByPlaceholderText("Digite o nome")).toBeInTheDocument()
    expect(screen.getByPlaceholderText("Digite o cpf")).toBeInTheDocument()
  })

  it("deve enviar formulário", async () => {
    const onSubmit = jest.fn().mockResolvedValue(undefined)

    renderComponent({ onSubmit })

    await waitFor(() => expect(listarPauta).toHaveBeenCalled())

    fireEvent.change(screen.getByPlaceholderText("Digite o nome"), {
      target: { value: "Kalebe" },
    })

    fireEvent.change(screen.getByPlaceholderText("Digite o cpf"), {
      target: { value: "12345678900" },
    })

    fireEvent.change(screen.getByRole("combobox"), {
      target: { value: VotoEnum.ACEITAR },
    })

    fireEvent.click(screen.getByText("Criar"))

    await waitFor(() => {
      expect(onSubmit).toHaveBeenCalled()
    })
  })

  it("deve chamar cancelar", async () => {
    const onCancel = jest.fn()

    renderComponent({ onCancel })

    fireEvent.click(screen.getByText("Cancelar"))

    expect(onCancel).toHaveBeenCalled()
  })
})