	.text
	.file	"irFileName"
	.globl	main                    # -- Begin function main
	.p2align	4, 0x90
	.type	main,@function
main:                                   # @main
	.cfi_startproc
# %bb.0:
	pushq	%rbx
	.cfi_def_cfa_offset 16
	subq	$16, %rsp
	.cfi_def_cfa_offset 32
	.cfi_offset %rbx, -16
	movl	$1, 12(%rsp)
	movl	$1, %ebx
	testl	%ebx, %ebx
	je	.LBB0_3
	.p2align	4, 0x90
.LBB0_2:                                # %label2
                                        # =>This Inner Loop Header: Depth=1
	movl	$.L.1arg_str, %edi
	movl	$1, %esi
	xorl	%eax, %eax
	callq	printf
	movl	$10, 12(%rsp)
	testl	%ebx, %ebx
	jne	.LBB0_2
.LBB0_3:                                # %label3
	xorl	%eax, %eax
	addq	$16, %rsp
	popq	%rbx
	retq
.Lfunc_end0:
	.size	main, .Lfunc_end0-main
	.cfi_endproc
                                        # -- End function
	.type	.L.1arg_str,@object     # @.1arg_str
	.section	.rodata.str1.1,"aMS",@progbits,1
.L.1arg_str:
	.asciz	"%d\n"
	.size	.L.1arg_str, 4


	.section	".note.GNU-stack","",@progbits
